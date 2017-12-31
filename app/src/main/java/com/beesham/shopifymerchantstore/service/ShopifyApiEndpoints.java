package com.beesham.shopifymerchantstore.service;

import com.beesham.shopifymerchantstore.model.Product;
import com.beesham.shopifymerchantstore.model.ProductsList;
import com.beesham.shopifymerchantstore.model.SingleProduct;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Beesham on 12/27/2017.
 */

public interface ShopifyApiEndpoints {

    @GET("products.json")
    Call<ProductsList> getProducts(@Query("page") String pageNumber,
                                   @Query("access_token") String authToken);

    @GET("products/{product_id}.json")
    Call<SingleProduct> getSingleProduct(@Path("product_id") String productId,
                                                 @Query("access_token") String authToken);

}
