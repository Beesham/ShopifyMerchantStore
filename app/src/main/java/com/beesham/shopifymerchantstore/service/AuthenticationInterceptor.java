package com.beesham.shopifymerchantstore.service;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Beesham on 12/27/2017.
 */

public class AuthenticationInterceptor implements Interceptor{

    private static final String LOG_TAG = AuthenticationInterceptor.class.getSimpleName();
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
                    .addHeader("Authorization", authToken).build();
        }

        //String url = original.url().toString();
       // Log.d(LOG_TAG, url);
        return chain.proceed(original);
    }
}
