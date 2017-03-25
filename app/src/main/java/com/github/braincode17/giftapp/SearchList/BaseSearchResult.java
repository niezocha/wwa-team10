package com.github.braincode17.giftapp.SearchList;


public class BaseSearchResult {

    private String itemId;
    private String name;
    private String itemImage;
    private String itemPrice;
    private String shippPrice;
    private String shippTime;

    public String getName() {
        return name;
    }

    public BaseSearchResult(String itemId, String itemImage, String name, String itemPrice, String shippPrice, String shippTime) {
        this.itemId = itemId;
        this.itemImage = itemImage;
        this.name = name;
        this.itemPrice = itemPrice;
        this.shippPrice = shippPrice;
        this.shippTime = shippTime;
    }

    public String getShippPrice() {
        return shippPrice;
    }

    public String getShippTime() {
        return shippTime;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemImage() {
        return itemImage;
    }


    public String getItemPrice() {
        return itemPrice;
    }


}
