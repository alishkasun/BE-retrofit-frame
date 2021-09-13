package ru.geekbrains.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.geekbrains.dto.Category;
import ru.geekbrains.dto.InvalidCategory;

public interface InvalidService {
    @GET("categories/{id}")
    Call<InvalidCategory> getInvalidCategory(@Path("id") int id);
}
