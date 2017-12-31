package com.beesham.shopifymerchantstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Beesham on 12/30/2017.
 */

public class SingleProduct {
    @SerializedName("product")
    @Expose
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
