package com.github.braincode17.giftapp.dao;


import java.util.List;

public interface InterfaceItemsDao {

    public List<SearchItem> fetchAllItems();
    public boolean addItems (List<SearchItem> items);
    public SearchItem fetchSingleItem(String [] itemIdArg);
    public boolean deleteItem(SearchItem item);
}
