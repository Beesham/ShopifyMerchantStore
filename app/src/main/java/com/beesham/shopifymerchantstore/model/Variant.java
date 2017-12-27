
package com.beesham.shopifymerchantstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variant {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("position")
    @Expose
    private int position;
    @SerializedName("inventory_policy")
    @Expose
    private String inventoryPolicy;
    @SerializedName("compare_at_price")
    @Expose
    private Object compareAtPrice;
    @SerializedName("fulfillment_service")
    @Expose
    private String fulfillmentService;
    @SerializedName("inventory_management")
    @Expose
    private Object inventoryManagement;
    @SerializedName("option1")
    @Expose
    private String option1;
    @SerializedName("option2")
    @Expose
    private Object option2;
    @SerializedName("option3")
    @Expose
    private Object option3;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("taxable")
    @Expose
    private boolean taxable;
    @SerializedName("barcode")
    @Expose
    private Object barcode;
    @SerializedName("grams")
    @Expose
    private int grams;
    @SerializedName("image_id")
    @Expose
    private Object imageId;
    @SerializedName("inventory_quantity")
    @Expose
    private int inventoryQuantity;
    @SerializedName("weight")
    @Expose
    private double weight;
    @SerializedName("weight_unit")
    @Expose
    private String weightUnit;
    @SerializedName("inventory_item_id")
    @Expose
    private int inventoryItemId;
    @SerializedName("old_inventory_quantity")
    @Expose
    private int oldInventoryQuantity;
    @SerializedName("requires_shipping")
    @Expose
    private boolean requiresShipping;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getInventoryPolicy() {
        return inventoryPolicy;
    }

    public void setInventoryPolicy(String inventoryPolicy) {
        this.inventoryPolicy = inventoryPolicy;
    }

    public Object getCompareAtPrice() {
        return compareAtPrice;
    }

    public void setCompareAtPrice(Object compareAtPrice) {
        this.compareAtPrice = compareAtPrice;
    }

    public String getFulfillmentService() {
        return fulfillmentService;
    }

    public void setFulfillmentService(String fulfillmentService) {
        this.fulfillmentService = fulfillmentService;
    }

    public Object getInventoryManagement() {
        return inventoryManagement;
    }

    public void setInventoryManagement(Object inventoryManagement) {
        this.inventoryManagement = inventoryManagement;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public Object getOption2() {
        return option2;
    }

    public void setOption2(Object option2) {
        this.option2 = option2;
    }

    public Object getOption3() {
        return option3;
    }

    public void setOption3(Object option3) {
        this.option3 = option3;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    public Object getBarcode() {
        return barcode;
    }

    public void setBarcode(Object barcode) {
        this.barcode = barcode;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public Object getImageId() {
        return imageId;
    }

    public void setImageId(Object imageId) {
        this.imageId = imageId;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public int getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(int inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public int getOldInventoryQuantity() {
        return oldInventoryQuantity;
    }

    public void setOldInventoryQuantity(int oldInventoryQuantity) {
        this.oldInventoryQuantity = oldInventoryQuantity;
    }

    public boolean isRequiresShipping() {
        return requiresShipping;
    }

    public void setRequiresShipping(boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }

}
