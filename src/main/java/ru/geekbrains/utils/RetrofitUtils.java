package ru.geekbrains.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitUtils {
    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new PrettyLogger());
    static OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://80.78.248.82:8189/market/api/v1/")
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
