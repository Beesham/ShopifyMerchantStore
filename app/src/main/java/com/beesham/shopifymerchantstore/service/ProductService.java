package com.beesham.shopifymerchantstore.service;

import android.text.TextUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Beesham on 12/27/2017.
 */

public class ProductService {

    public static final String SHOPIFY_BASE_URL = "https://shopicruit.myshopify.com/admin/";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(SHOPIFY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if(!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);
        }
    }

}
