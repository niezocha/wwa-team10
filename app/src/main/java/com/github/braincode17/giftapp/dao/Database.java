package com.github.braincode17.giftapp.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

    public static ItemsDao itemsDao;
    DbHelper dbHelper;


    public Database(Context context) {
        this.dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        itemsDao = new ItemsDao(db);

    }

    public class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, ItemsContract.DB_NAME, null, ItemsContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + ItemsContract.ItemEntry.TABLE + " ("
                    + ItemsContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ItemsContract.ItemEntry.COL_ITEM_NAME + " TEXT, "
                    + ItemsContract.ItemEntry.COL_ITEM_IMAGE + " TEXT, "
                    + ItemsContract.ItemEntry.COL_ITEM_PRICE + " TEXT, "
                    + ItemsContract.ItemEntry.COL_ITEM_SHIPP_TIME + " TEXT);";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ItemsContract.ItemEntry.TABLE);
            onCreate(db);
        }
    }


}
