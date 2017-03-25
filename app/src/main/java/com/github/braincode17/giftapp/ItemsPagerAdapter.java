package com.github.braincode17.giftapp;

import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.braincode17.giftapp.SearchList.BaseSearchResult;

import java.util.List;

public class ItemsPagerAdapter extends PagerAdapter {

    RecyclerView recyclerView;

    private SharedPreferences sharedPreferences;
    private List<BaseSearchResult> items;



    public void setItems(List<BaseSearchResult> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public ItemsPagerAdapter(List<BaseSearchResult> items, SharedPreferences preferences) {
        this.items = items;
        this.sharedPreferences=preferences;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.view_layout, container, false);


        recyclerView = (RecyclerView) container.findViewById(R.id.recycler_view);







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
