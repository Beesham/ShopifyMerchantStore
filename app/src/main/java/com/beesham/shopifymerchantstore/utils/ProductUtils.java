package com.beesham.shopifymerchantstore.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.beesham.shopifymerchantstore.data.Columns;
import com.beesham.shopifymerchantstore.data.ProductProvider;
import com.beesham.shopifymerchantstore.model.Product;
import com.beesham.shopifymerchantstore.model.ProductsList;
import com.beesham.shopifymerchantstore.model.Variant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Beesham on 12/29/2017.
 */

public class ProductUtils {

    private static final String LOG_TAG = ProductUtils.class.getSimpleName();

    public static Vector<ContentValues> prepareProductsForCaching(ProductsList productsList) {

        Vector<ContentValues> contentValuesVector = new Vector<>(productsList.getProducts().size());

        //Converts the data in the productsList to contentValues in order for caching in database
        for(Product product: productsList.getProducts()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Columns.ProductColumns.TITLE, product.getTitle());
            contentValues.put(Columns.ProductColumns.PRODUCT_ID, product.getId());
            contentValues.put(Columns.ProductColumns.TAGS, product.getTags());
            contentValues.put(Columns.ProductColumns.VENDOR, product.getVendor());
            contentValues.put(Columns.ProductColumns.IMAGE_URL, product.getImage().getSrc());
            contentValues.put(Columns.ProductColumns.DESCRIPTION, product.getBodyHtml());
            contentValues.put(Columns.ProductColumns.PRODUCT_TYPE, product.getProductType());

            contentValuesVector.add(contentValues);
        }

        //TODO: contentvaluse for options

        return contentValuesVector;
    }

    public static ArrayList<ContentValues> prepareProductVariantsForCaching(ProductsList productsList) {

        ArrayList<ContentValues> variantsContentValuesList = new ArrayList<>();

        //Converts the data in the productsList to contentValues in order for caching in database
        for(Product product: productsList.getProducts()) {
            List<Variant> variantList = product.getVariants();
            for(Variant variant: variantList) {
                ContentValues variantContentValues = new ContentValues();
                variantContentValues.put(Columns.VariantColumns.VARIANT_ID, variant.getId());
                variantContentValues.put(Columns.VariantColumns.PRODUCT_ID, variant.getProductId());
                variantContentValues.put(Columns.VariantColumns.TITLE, variant.getTitle());
                variantContentValues.put(Columns.VariantColumns.PRICE, variant.getPrice());
                variantContentValues.put(Columns.VariantColumns.INVENTORY_QUANTITY, variant.getInventoryQuantity());

                variantsContentValuesList.add(variantContentValues);
            }
        }

        return variantsContentValuesList;
    }

    public static void logProductVariants(Context context, ArrayList<ContentValues> contentValues) {
        int inserted = 0;

        if(contentValues.size() > 0){
            ContentValues[] contentValuesArray = new ContentValues[contentValues.size()];
            contentValues.toArray(contentValuesArray);
            inserted = context.getContentResolver().bulkInsert(ProductProvider.Variant.CONTENT_URI, contentValuesArray);
        }
    }

    public static void logProducts(Context context, Vector<ContentValues> contentValuesVector) {
        int inserted = 0;

        if(contentValuesVector.size() > 0){
            ContentValues[] contentValuesArray = new ContentValues[contentValuesVector.size()];
            contentValuesVector.toArray(contentValuesArray);
            inserted = context.getContentResolver().bulkInsert(ProductProvider.Product.CONTENT_URI, contentValuesArray);
        }
    }

    /**
     * Filters a cursor with multiple entries to remove duplicates
     * @param data
     * @return list of unique entries
     */
    public static Set<String> filterTags(Cursor data) {
        Set<String> tags = new TreeSet<>();
        while(data.moveToNext()) {
            String s = data.getString(data.getColumnIndex(Columns.ProductColumns.TAGS));
            List<String> list = Arrays.asList(s.split(","));
            for (String tag: list) {
                tags.add(tag.trim());
            }
        }
        return tags;
    }

}
