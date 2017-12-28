package com.beesham.shopifymerchantstore.service;

import com.beesham.shopifymerchantstore.model.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Beesham on 12/27/2017.
 */

public interface ShopifyApiEndpoints {

    @GET("products.json")
    Call<Product> getProducts(@Query("page") String pageNumber);

}
