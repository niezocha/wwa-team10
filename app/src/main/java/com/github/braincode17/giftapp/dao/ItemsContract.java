package com.github.braincode17.giftapp.dao;


import android.provider.BaseColumns;

public class ItemsContract {

    public static final String DB_NAME = "itemsDb";
    public static final int DB_VERSION = 1;




    public class ItemEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_ITEM_ID = "itemId";
        public static final String COL_ITEM_NAME = "name";
        public static final String COL_ITEM_IMAGE = "itemImage";
        public static final String COL_ITEM_PRICE = "itemPrcie";
        public static final String COL_ITEM_SHIPP_TIME = "shippTime";


    }

    public static String[] COLUMNS_NAMES = {ItemEntry._ID, ItemEntry.COL_ITEM_ID, ItemEntry.COL_ITEM_NAME, ItemEntry.COL_ITEM_IMAGE, ItemEntry.COL_ITEM_PRICE, ItemEntry.COL_ITEM_SHIPP_TIME};


}
