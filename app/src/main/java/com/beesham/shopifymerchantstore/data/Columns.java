package com.beesham.shopifymerchantstore.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Beesham on 12/27/2017.
 */

public class Columns {

    public static class ProductColumns {
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        public static final String _ID = "_id";

        @DataType(DataType.Type.INTEGER)
        @Unique(onConflict = ConflictResolutionType.REPLACE)
        @NotNull
        public static final String PRODUCT_ID = "product_id";

        @DataType(DataType.Type.TEXT)
        public static final String TITLE = "title";

        @DataType(DataType.Type.TEXT)
        public static final String VENDOR = "vendor";

        @DataType(DataType.Type.TEXT)
        public static final String TAGS = "tags";

        @DataType(DataType.Type.TEXT)
        public static final String IMAGE_URL = "image_url";

        @DataType(DataType.Type.TEXT)
        public static final String DESCRIPTION = "description";

        @DataType(DataType.Type.TEXT)
        public static final String PRODUCT_TYPE = "product_type";

    }

    public static class VariantColumns {
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        public static final String _ID = "_id";

        @DataType(DataType.Type.INTEGER)
        @Unique(onConflict = ConflictResolutionType.REPLACE)
        @NotNull
        public static final String VARIANT_ID = "variant_id";

        @DataType(DataType.Type.INTEGER)
        @NotNull
        public static final String PRODUCT_ID = "product_id";

        @DataType(DataType.Type.TEXT)
        public static final String TITLE = "title";

        @DataType(DataType.Type.REAL)
        public static final String PRICE = "price";

        @DataType(DataType.Type.INTEGER)
        public static final String  INVENTORY_QUANTITY = "inventory_quantity";
    }

    public static class OptionColumns {
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        @AutoIncrement
        public static final String _ID = "_id";

        @DataType(DataType.Type.INTEGER)
        @NotNull
        public static final String OPTIONS_ID = "options_id";

        @DataType(DataType.Type.INTEGER)
        @NotNull
        public static final String PRODUCT_ID = "product_id";

        @DataType(DataType.Type.TEXT)
        public static final String VALUES = "options_values";
    }
}
