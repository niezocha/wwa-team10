package com.github.braincode17.giftapp.SearchList;


import android.util.Log;

import java.math.BigDecimal;

public class SingleSearchResult {

    private String id;
    private String name;
    private BigDecimal price;
    private int addedAt;
    private int endAt;
    private String status;
    private String itemId;
    private String condition;
    private int currentQty;
    private int deliveryCost;
    private boolean isFreeDelivery;
    private int deliveryTime;
    private int startQty;
    private boolean isRecommended;
    private SingleGalleryImage galleryImage;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        Log.d("cena", price.toString());
        return price;
    }

    public int getAddedAt() {
        return addedAt;
    }

    public int getEndAt() {
        return endAt;
    }

    public String getStatus() {
        return status;
    }

    public String getItemId() {
        return itemId;
    }

    public String getCondition() {
        return condition;
    }

    public int getCurrentQty() {
        return currentQty;
    }

    public int getDeliveryCost() {
        Log.d("koszt dostawy", String.valueOf(deliveryCost));
        return deliveryCost;
    }

    public boolean isFreeDelivery() {
        return isFreeDelivery;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int getStartQty() {
        return startQty;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public SingleGalleryImage getGalleryImage() {

        return galleryImage;
    }
}
