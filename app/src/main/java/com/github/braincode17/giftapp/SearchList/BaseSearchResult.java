package com.github.braincode17.giftapp.SearchList;


public class BaseSearchResult {

    private String itemId;
    private String itemImage;

    private String itemPrice;

    public BaseSearchResult(String itemId, String itemImage, String itemPrice) {
        this.itemId = itemId;
        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
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
