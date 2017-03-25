package com.github.braincode17.giftapp;

import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.braincode17.giftapp.SearchList.BaseSearchResult;

import java.util.List;

public class ItemsPagerAdapter extends PagerAdapter {

    private SharedPreferences sharedPreferences;
    private List<BaseSearchResult> items;

    public ItemsPagerAdapter(List<BaseSearchResult> items, SharedPreferences preferences) {
        this.items = items;
        this.sharedPreferences=preferences;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.listing_item_layout, container, false);

        container.addView(view);
        return view;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
