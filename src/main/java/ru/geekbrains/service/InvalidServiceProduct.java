package ru.geekbrains.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.geekbrains.dto.InvalidCategory;
import ru.geekbrains.dto.InvalidProduct;

public interface InvalidServiceProduct {
    @GET("products/{id}")
    Call<InvalidProduct> getInvalidProduct(@Path("id") int id);
}

