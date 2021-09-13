package ru.geekbrains.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import retrofit2.Response;
import ru.geekbrains.dto.Product;
import ru.geekbrains.enums.Category;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

public class ProductsTest extends BaseTest {
    Product product;
    Product product_invalid;
    Integer id;

    @BeforeEach
    void setUp() {
       product=  new Product()
                .withTitle(faker.food().dish())
                .withCategoryTitle(Category.FOOD.getName())
                .withPrice(1000);
    }

    // Отправка запроса с валидными данными.
    @Test
    void createProductWithIntPriceTest() throws IOException {

        Response<Product> response = productService
                .createProduct(product)
                .execute();
        id = response.body().getId();
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getId()).isNotNull();
    }

//Не валидный title
    @Test
    void createProductWithInvalidTitleTest() throws IOException {

        product_invalid = new Product()
                .withTitle(faker.food().dish())
                .withCategoryTitle(Category.INVALID_TITLE.getName())
                .withPrice(-1000);

        Response<Product> response = productService
                .createProduct(product_invalid)
                .execute();
        id = response.body().getId();
        assertThat(response.errorBody()).isNotEqualTo(product.getCategoryTitle() );

    }

    //Получение данных по существующему id
    @ParameterizedTest
    @EnumSource(value = ru.geekbrains.enums.Product.class)
    void getFoodProductTest(ru.geekbrains.enums.Product product) throws IOException {

        Response<Product> response = productService
                .getProduct(product.getId())
                .execute();

        assertThat(response.isSuccessful());
        assertThat(response.body().getTitle()).isEqualTo(product.getName());
        assertThat(response.body().getId()).isEqualTo(id);
    }

    @Test
    void updateProductWithIntPriceTest() throws IOException {

        Response<Product> response = productService
                .createProduct(product)
                .execute();
        id = response.body().getId();
        assertThat(response.body().getCategoryTitle()).isEqualTo(product.getCategoryTitle());
        assertThat(response.body().getTitle()).isEqualTo(product.getTitle());
        assertThat(response.body().getPrice()).isEqualTo(product.getPrice());
        assertThat(response.body().getId()).isNotNull();
    }
    @AfterEach
    void tearDown() throws IOException {
        productService.deleteProduct(id).execute();
    }
}


///**
// * API tests for ProductControllerApi
// */
//public class ProductControllerApiTest {
//
//    private ProductControllerApi api;
//
//    @Before
//    public void setup() {
//        api = new ApiClient().createService(ProductControllerApi.class);
//    }
//
//
//    /**
//     * Creates a new product. If id !&#x3D; null, then throw bad request response
//     *
//     *
//     */
//    @Test
//    public void createNewProductUsingPOSTTest() {
//        ProductDto p = null;
//        // Object response = api.createNewProductUsingPOST(p);
//
//        // TODO: test validations
//    }
//
//    /**
//     * Delete product
//     *
//     *
//     */
//    @Test
//    public void deleteByIdUsingDELETETest() {
//        Long id = null;
//        // api.deleteByIdUsingDELETE(id);
//
//        // TODO: test validations
//    }
//
//    /**
//     * Returns a specific product by their identifier. 404 if does not exist.
//     *
//     *
//     */
//    @Test
//    public void getProductByIdUsingGETTest() {
//        Long id = null;
//        // ProductDto response = api.getProductByIdUsingGET(id);
//
//        // TODO: test validations
//    }
//
//    /**
//     * Returns products
//     *
//     *
//     */
//    @Test
//    public void getProductsUsingGETTest() {
//        // List<ProductDto> response = api.getProductsUsingGET();
//
//        // TODO: test validations
//    }
//
//    /**
//     * Modify product
//     *
//     *
//     */
//    @Test
//    public void modifyProductUsingPUTTest() {
//        ProductDto p = null;
//        // Object response = api.modifyProductUsingPUT(p);
//
//        // TODO: test validations
//    }
//
//}