package com.example.rxjavademo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "https://api.github.com/";
    private static Retrofit.Builder builder=new Retrofit.Builder()
             .baseUrl(BASE_URL) // 设置 网络请求 Url
            .addConverterFactory(GsonConverterFactory.create()) ;
    private static Retrofit retrofit=builder.build();

    private static HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpBuilder=new OkHttpClient.Builder();



    public static <S> S createService(Class<S> serviceClass){
        if (!httpBuilder.interceptors().contains(loggingInterceptor)) {
            httpBuilder.addInterceptor(loggingInterceptor);
            builder = builder.client(httpBuilder.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    };

}