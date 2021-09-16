package ru.geekbrains.tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import retrofit2.Retrofit;
import ru.geekbrains.db.dao.CategoriesMapper;
import ru.geekbrains.db.dao.ProductsMapper;
import ru.geekbrains.db.model.Categories;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.InvalidService;
import ru.geekbrains.service.InvalidServiceProduct;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.utils.DbUtils;
import ru.geekbrains.utils.RetrofitUtils;

public class BaseTest {
    static Retrofit retrofit;
    static CategoryService categoryService;
    static InvalidService invalidService;
    static ProductService productService;
    static InvalidServiceProduct invalidServiceProduct;
    static Faker faker;
    static ProductsMapper productsMapper;
    static CategoriesMapper categoriesMapper;
    static Categories testCategory;

    @BeforeAll
    static void beforeAll() {
        retrofit = RetrofitUtils.getRetrofit();
        categoryService = retrofit.create(CategoryService.class);
        productService = retrofit.create(ProductService.class);
        faker = new Faker();
        invalidService = retrofit.create(InvalidService.class);
        invalidServiceProduct = retrofit.create(InvalidServiceProduct.class);

        productsMapper = DbUtils.getProductsMapper();
        categoriesMapper = DbUtils.getCategoriesMapper();

        String quote = faker.backToTheFuture().quote();
        //создаем новую категорию
        testCategory = DbUtils.getNewTestCategory(quote);
    }

    @AfterAll
    static void afterAll(){
        DbUtils.deleteAllProductsWithTheCategory(testCategory.getId());
        categoriesMapper.deleteByPrimaryKey(testCategory.getId());
    }
}
