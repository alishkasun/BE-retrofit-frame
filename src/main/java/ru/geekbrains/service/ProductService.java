package ru.geekbrains.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.geekbrains.dto.Category;
import ru.geekbrains.dto.Product;

public interface ProductService {
    @POST("products")
    Call<Product> createProduct(@Body Product product);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") Integer id);
}

