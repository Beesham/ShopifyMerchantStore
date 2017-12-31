package com.beesham.shopifymerchantstore.service;

import android.text.TextUtils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Beesham on 12/27/2017.
 */

public class ProductServiceGenerator {

    public static final String SHOPIFY_BASE_URL = "https://shopicruit.myshopify.com/admin/";

    private static OkHttpClient client = new OkHttpClient().newBuilder().build();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(SHOPIFY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    //Logs the response
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static <S> S createService(Class<S> serviceClass) {
        client = client.newBuilder()
                .addInterceptor(logging)
                .build();
        retrofitBuilder = retrofit.newBuilder();
        retrofit = retrofitBuilder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if(!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if(!client.interceptors().contains(interceptor)){
                client = client.newBuilder()
                        .addInterceptor(interceptor)
                        .addInterceptor(logging)
                        .build();
                retrofitBuilder = retrofit.newBuilder();
                retrofit = retrofitBuilder.client(client).build();
            }
        }

        return retrofit.create(serviceClass);
    }

}
