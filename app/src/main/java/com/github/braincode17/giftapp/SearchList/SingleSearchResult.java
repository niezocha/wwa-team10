package com.github.braincode17.giftapp.SearchList;


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
    private int daliveryTime;
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
        return deliveryCost;
    }

    public boolean isFreeDelivery() {
        return isFreeDelivery;
    }

    public int getDaliveryTime() {
        return daliveryTime;
    }

    public int getStartQty() {
        return startQty;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

   public SingleGalleryImage getGalleryImage(){
       return galleryImage;
   }
}
