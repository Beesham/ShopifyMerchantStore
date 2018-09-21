package com.beesham.shopifymerchantstore.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.Join;
import net.simonvt.schematic.annotation.MapColumns;
import net.simonvt.schematic.annotation.TableEndpoint;
import net.simonvt.schematic.annotation.Where;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beesham on 12/27/2017.
 */

@ContentProvider(authority = ProductProvider.AUTHORITY, database = ProductDatabase.class)
public final class ProductProvider {

    public static final String AUTHORITY = "com.beesham.shopifymerchantstore.data.ProductProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    interface Path{
        String PRODUCT = "product";
        String VARIANT = "variant";
        String VARIANT_JOIN = "variant_join";
        String OPTION = "option";
    }

    private static Uri buildUri(String... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for(String path:paths){
            builder.appendPath(path);
        }

        return builder.build();
    }

    @TableEndpoint(table = ProductDatabase.PRODUCT)
    public static class Product {

        @ContentUri(
                path = Path.PRODUCT,
                type = "vnd.android.cursor.dir/product"
        )
        public static final Uri CONTENT_URI = buildUri(Path.PRODUCT);

        @InexactContentUri(
                name = "PRODUCT_ID",
                path = Path.PRODUCT + "/*",
                type = "vnd.android.cursor.item/product",
                whereColumn = Columns.ProductColumns.TITLE,
                pathSegment = 1
        )
        public static Uri withTitle(String productTitle) {
            return buildUri(Path.PRODUCT, productTitle);
        }

    }

    @TableEndpoint(table = ProductDatabase.VARIANT)
    public static class Variant {
        @ContentUri(
                path = Path.VARIANT,
                type = "vnd.android.cursor.dir/variant"
        )
        public static final Uri CONTENT_URI = buildUri(Path.VARIANT);

        @InexactContentUri(
                name = "VARIANT_ID",
                path = Path.VARIANT + "/*",
                type = "vnd.android.cursor.item/variant",
                whereColumn = Columns.ProductColumns.PRODUCT_ID,
                pathSegment = 1
        )
        public static Uri withID(String productID) {
            return buildUri(Path.VARIANT, productID);
        }

        @ContentUri(
                path = Path.VARIANT_JOIN,
                type = "vnd.android.cursor.dir/variant",
                join = "INNER JOIN product ON product.product_id=variant.product_id",
                groupBy = "variant.product_id"
        )
        public static final Uri CONTENT_URI_VARIANT_JOIN = buildUri(Path.VARIANT_JOIN);
    }

    //TODO: Option table endpoint


}
