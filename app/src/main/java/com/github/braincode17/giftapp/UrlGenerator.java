package com.github.braincode17.giftapp;

import java.util.HashMap;
import java.util.Map;

public class UrlGenerator {

    private String tag = "allegropl";
    private String price = "0";
    private String sort = "random";
    private String urlSearch = "http://inspiracje.allegro.pl/api/offers/";

    private static final String[] tags = {"inspirującego", "dla niej", "dla niego", "dla dziecka", "dla domu", "z prezentów"};
    private static final String[] tagsVal = {"allegropl", "dlaniej", "dlaniego", "dziecko", "dom", "gadzet"};

    private static final String[] prices = {"dowolnej kwoty", "do 50 zł", "do 100 zł", "do 200 zł"};
    private static final String[] pricesVal = {"0", "50", "100", "200"};

    private static final String[] sorting = {"losowo", "najtańszych", "najdroższych", "najpopularniejszych"};
    private static final String[] sortingVal = {"random", "cheapest", "most_expencive", "most_popular"};

    Map<String, String> tagsMap;
    Map<String, String> pricesMap;
    Map<String, String> sortingsMap;

    {
        tagsMap = generateMap(tags, tagsVal);
        pricesMap = generateMap(prices, pricesVal);
        sortingsMap = generateMap(sorting, sortingVal);
    }

    private String tagSelected;
    private String priceSelected;
    private String sortSelected;

    public static String[] getTags() {
        return tags;
    }

    public static String[] getPrices() {
        return prices;
    }

    public static String[] getSorting() {
        return sorting;
    }

    public String getTag() {
        return tag;
    }

    public String getPrice() {
        return price;
    }

    public String getSort() {
        return sort;
    }

    public String getUrlSearch() {
        return urlSearch;
    }

    public void setTag() {
        String tag = tagsMap.get(tagSelected);
        this.tag = tag;
    }

    public void setPrice() {
        String price = pricesMap.get(priceSelected);
        this.price = price;
    }

    public void setSort() {
        String sort = sortingsMap.get(sortSelected);
        this.sort = sort;
    }

    public String getTagSelected() {
        return tagSelected;
    }

    public String getPriceSelected() {
        return priceSelected;
    }

    public String getSortSelected() {
        return sortSelected;
    }

    public void setTagSelected(String tagSelected) {
        this.tagSelected = tagSelected;
    }

    public void setPriceSelected(String priceSelected) {
        this.priceSelected = priceSelected;
    }

    public void setSortSelected(String sortSelected) {
        this.sortSelected = sortSelected;
    }

    public Map<String, String> generateMap(String[] list1, String[] list2) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], list2[i]);
        }
        return map;
    }

    public void updateUrlGenerator(){
        setTag();
        setPrice();
        setSort();
    }
}
