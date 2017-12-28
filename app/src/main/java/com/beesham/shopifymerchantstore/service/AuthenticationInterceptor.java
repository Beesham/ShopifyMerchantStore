package com.beesham.shopifymerchantstore.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Beesham on 12/27/2017.
 */

public class AuthenticationInterceptor implements Interceptor{

    private String authToken;

    public AuthenticationInterceptor(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        if (authToken != null && !authToken.isEmpty())
        {
            /*
             Using ".header("Authorization", authToken)”, will overwrite
             any old Authorization header values, which we want to do.
            */
            original = original.newBuilder()
                    .header("Authorization", authToken).build();
        }
        return chain.proceed(original);
    }
}
