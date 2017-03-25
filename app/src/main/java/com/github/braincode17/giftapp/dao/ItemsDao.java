package com.github.braincode17.giftapp.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ItemsDao extends DbContentProvider implements InterfaceItemsDao {

    protected ItemsDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public List<SearchItem> fetchAllItems() {
        Cursor cursor = query(ItemsContract.ItemEntry.TABLE, ItemsContract.COLUMNS_NAMES, null, null);
        List<SearchItem> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(ItemsContract.ItemEntry._ID);
            int id = cursor.getInt(idx);
            int itemIdIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_ID);
            String itemId = cursor.getString(itemIdIndex);
            int itemNameIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_NAME);
            String itemName = cursor.getString(itemNameIndex);
            int itemImageIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_IMAGE);
            String itemImage = cursor.getString(itemImageIndex);
            int itemPriceIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_PRICE);
            String itemPrice = cursor.getString(itemPriceIndex);
            int shippTimeIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_SHIPP_TIME);
            String shippTime = cursor.getString(shippTimeIndex);
            SearchItem sa = new SearchItem(itemId, itemName, itemImage, itemPrice, shippTime);
            list.add(sa);
        }

        return list;
    }

    @Override
    public boolean addItems(List<SearchItem> items) {

        int counter = 0;
        boolean flag = true;
        while (flag)
            for (SearchItem searchIt : items) {
                ContentValues values = new ContentValues();
                values.put(ItemsContract.ItemEntry.COL_ITEM_ID, searchIt.getItemId());
                values.put(ItemsContract.ItemEntry.COL_ITEM_NAME, searchIt.getItemName());
                values.put(ItemsContract.ItemEntry.COL_ITEM_IMAGE, searchIt.getItemImage());
                values.put(ItemsContract.ItemEntry.COL_ITEM_PRICE, searchIt.getItemPrice());
                values.put(ItemsContract.ItemEntry.COL_ITEM_SHIPP_TIME, searchIt.getShippTime());
                flag = insert(ItemsContract.ItemEntry.TABLE, values);
                counter++;
            }
        return (counter > 0)? true : false;
    }

    @Override
    public SearchItem fetchSingleItem(String [] itemIdArg) {

        Cursor cursor = query(ItemsContract.ItemEntry.TABLE, ItemsContract.COLUMNS_NAMES, ItemsContract.ItemEntry.COL_ITEM_ID, itemIdArg);
        int idx = cursor.getColumnIndex(ItemsContract.ItemEntry._ID);
        int id = cursor.getInt(idx);
        int itemIdIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_ID);
        String itemId = cursor.getString(itemIdIndex);
        int itemNameIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_NAME);
        String itemName = cursor.getString(itemNameIndex);
        int itemImageIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_IMAGE);
        String itemImage = cursor.getString(itemImageIndex);
        int itemPriceIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_PRICE);
        String itemPrice = cursor.getString(itemPriceIndex);
        int shippTimeIndex = cursor.getColumnIndex(ItemsContract.ItemEntry.COL_ITEM_SHIPP_TIME);
        String shippTime = cursor.getString(shippTimeIndex);
        SearchItem sa = new SearchItem(itemId, itemName, itemImage, itemPrice, shippTime);
        return sa;
    }

    @Override
    public boolean deleteItem(SearchItem item) {
        String selectionString = ItemsContract.ItemEntry._ID;
        String [] selectionArgs = {item.getItemId()};
        return delete(ItemsContract.ItemEntry.TABLE, selectionString, selectionArgs);
    }
}
