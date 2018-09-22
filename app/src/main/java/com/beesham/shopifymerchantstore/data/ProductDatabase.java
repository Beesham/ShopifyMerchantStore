package com.beesham.shopifymerchantstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.IfNotExists;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Beesham on 12/27/2017.
 */

@Database(version = ProductDatabase.VERSION)
public class ProductDatabase {
    public static final int VERSION = 1;

    @Table(Columns.ProductColumns.class) @IfNotExists public static final String PRODUCT = "product";
    @Table(Columns.VariantColumns.class) @IfNotExists public static final String VARIANT = "variant";
    @Table(Columns.OptionColumns.class) @IfNotExists public static final String OPTION = "option";

}
