package ru.geekbrains.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import retrofit2.Response;
import ru.geekbrains.dto.InvalidProduct;
import ru.geekbrains.dto.Product;
import ru.geekbrains.enums.Category;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

public class ProductsTest extends BaseTest {
    ObjectMapper objectMapper = new ObjectMapper();

    private int deleteId;
    private int idForDelet;

    private Product createProduct() {
       return new Product()
                .withTitle(faker.food().dish())
                .withCategoryTitle(Category.FOOD.getName())
                .withPrice(1000);
    }


       // Отправка POST запроса с валидными данными.
    @Test
    void createProductWithIntPriceTest() throws IOException {
        var product = createProduct();
        Response<Product> response = productService
                .createProduct(product)
                .execute();

        deleteId = response.body().getId();

        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getId()).isNotNull();
    }


    //Получение данных по существующему id GET products/id
//    {
//        "id": 11329,
//            "title": "Ebiten maki",
//            "price": 196,
//            "categoryTitle": "Food"
//    }
    @Test
    void getFoodProductTest() throws IOException {
        Response<Product> response = productService
                .getProduct(11329)
                .execute();
        //проверяем title
        assertThat(response.body().getTitle()).isEqualTo("Ebiten maki");
        // проверяем на ответ 200
        assertThat(response.code()).isEqualTo(200);
        //проверяем "price": 196
        assertThat(response.body().getPrice()).isNotZero();
    }


    //Проверка на несуществующую катеогию 5.
    @Test
    void createProductWithInvalidTitleTest() throws IOException {
        var product_invalid = new Product()
                .withTitle(faker.food().dish())
                .withCategoryTitle(Category.INVALID_TITLE.getName())
                .withPrice(-1000);

        Response<Product> response = productService
                .createProduct(product_invalid)
                .execute();

        Optional.ofNullable(response.body()).ifPresent(body -> {
            deleteId = body.getId();
        });
        assertThat(response.errorBody()).isNotNull();
    }

    //Проверка получение несуществующего продукта.

    @ParameterizedTest
    @EnumSource(value = ru.geekbrains.enums.InvalidP.class)
    void getInvalidProductTest(ru.geekbrains.enums.InvalidP invalidP) throws IOException {
        Response<InvalidProduct> response = invalidServiceProduct
                .getInvalidProduct(invalidP.getId())
                .execute();
        ResponseBody responseBody = response.errorBody();
        //Создаем клиент и возвращаем объект
        var invalidProduct = objectMapper.readValue(responseBody.bytes(), InvalidProduct.class);
        // проверка на status = 404
        // 1. Берем статус из ответа
        var expectedStatus = invalidProduct.getStatus();
        // 2. проверк 404
        assertThat(expectedStatus).isEqualTo(404);
        //проверяем massage
        var expectedMassage = invalidProduct.getMessage();
        assertThat(expectedMassage).isEqualTo(invalidP.getMessage());
    }

    //Удаление продукта - мне кажется удаление не происходит.

    @Test
    void deletProducTest() throws IOException {
        var product = createProduct();
        Response<Product> response = productService
                .createProduct(product)
                .execute();

        idForDelet = response.body().getId();

        Response<ResponseBody> responseBody = productService
                .deleteProduct(idForDelet)
                .execute();

        assertThat(response.isSuccessful());

    }


    @Test
    void updateProductWithIntPriceTest() throws IOException {
        var product = createProduct();
        Response<Product> response = productService
                .createProduct(product)
                .execute();

        deleteId = response.body().getId();

        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getId()).isNotNull();
    }

    @AfterEach
    void tearDown() throws IOException {
        productService.deleteProduct(deleteId).execute();
    }
}
