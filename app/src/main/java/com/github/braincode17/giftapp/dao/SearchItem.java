package com.github.braincode17.giftapp.dao;


public class SearchItem {

    protected String itemId;
    protected String itemName;
    protected String itemImage;
    protected String itemPrice;
    protected String shippTime;

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getShippTime() {
        return shippTime;
    }

    public SearchItem(String itemId, String itemName, String itemImage, String itemPrice, String shippTime) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
        this.shippTime = shippTime;
    }
}
