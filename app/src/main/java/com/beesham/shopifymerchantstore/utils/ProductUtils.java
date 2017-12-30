package com.beesham.shopifymerchantstore.utils;

import android.content.ContentValues;
import android.content.Context;

import com.beesham.shopifymerchantstore.data.Columns;
import com.beesham.shopifymerchantstore.data.ProductProvider;
import com.beesham.shopifymerchantstore.model.Product;
import com.beesham.shopifymerchantstore.model.ProductsList;

import java.util.Vector;

/**
 * Created by Beesham on 12/29/2017.
 */

public class ProductUtils {

    public static Vector<ContentValues> prepareForCaching(ProductsList productsList) {
        
        Vector<ContentValues> contentValuesVector = new Vector<>(productsList.getProducts().size());

        //Converts the data in the productsList to contentValues in order for caching in database
        for(Product product: productsList.getProducts()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Columns.ProductColumns.TITLE, product.getTitle());
            contentValues.put(Columns.ProductColumns.PRODUCT_ID, product.getId());
            contentValues.put(Columns.ProductColumns.TAGS, product.getTags());
            contentValues.put(Columns.ProductColumns.VENDOR, product.getVendor());
            //TODO: images

            contentValuesVector.add(contentValues);
        }

        //TODO: contentvaluse for options and variants

        return contentValuesVector;
    }

    public static void logProducts(Context context, ContentValues contentValues) {
        context.getContentResolver().insert(ProductProvider.Product.CONTENT_URI, contentValues);
    }

}
