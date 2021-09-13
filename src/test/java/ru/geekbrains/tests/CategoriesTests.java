package ru.geekbrains.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import retrofit2.Response;
import ru.geekbrains.dto.Category;
import ru.geekbrains.dto.InvalidCategory;
import ru.geekbrains.dto.Product;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesTests extends BaseTest {

    ObjectMapper objectMapper = new ObjectMapper();
    private Object Product;

    @ParameterizedTest
    @EnumSource(value = ru.geekbrains.enums.Category.class)
    void getFoodCategoryTest(ru.geekbrains.enums.Category category) throws IOException {
        Response<Category> response = categoryService
                .getCategory(category.getId())
                .execute();
        assertThat(response.body().getTitle()).isEqualTo(category.getName());
        assertThat(response.isSuccessful());

        response.
                body().
                getProducts()
                .forEach(e -> assertThat(e.getCategoryTitle()).isEqualTo(category.getName()));
//        try {
////            System.out.println(response.body().string());
//        }
//        catch (NullPointerException e){
////            System.out.println(response.errorBody().string());
//        }

    }


    //Проверка на несущестаующую категорию.
    @ParameterizedTest
    @EnumSource(value = ru.geekbrains.enums.Invalid.class, names = {"INVALID"})
    void getInvalidCategoryTest(ru.geekbrains.enums.Invalid invalid) throws IOException {
        Response<InvalidCategory> response = invalidService
                .getInvalidCategory(invalid.getId())
                .execute();
        ResponseBody responseBody = response.errorBody();
//
//        assertThat(responseBody).isNotNull();
//        assertThat(responseBody).isEqualTo(invalid.getName());

        var invalidCategory = objectMapper.readValue(responseBody.bytes(), InvalidCategory.class);
        assertThat(responseBody).isEqualTo(invalid.getName());
        assertThat(responseBody).isNotNull();
        assertThat(responseBody).isEqualTo(invalidCategory.getStatus());

    }

}


