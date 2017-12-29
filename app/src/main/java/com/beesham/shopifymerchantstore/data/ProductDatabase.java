package com.beesham.shopifymerchantstore.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Beesham on 12/27/2017.
 */

@Database(version = ProductDatabase.VERSION)
public class ProductDatabase {
    public static final int VERSION = 1;

    @Table(Columns.ProductColumns.class) public static final String PRODUCT = "product";
    @Table(Columns.VariantColumns.class) public static final String VARIANT = "variant";
    @Table(Columns.OptionColumns.class) public static final String OPTION = "option";

}
