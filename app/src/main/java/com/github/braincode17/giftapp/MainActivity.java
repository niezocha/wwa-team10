package com.github.braincode17.giftapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;


    List<SingleItem> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tabLayout.setupWithViewPager(viewPager);
        itemsList = new ArrayList<>();
        itemsList.add(new SingleItem());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ItemsPagerAdapter adapter = new ItemsPagerAdapter(itemsList, sharedPreferences);
        viewPager.setAdapter(adapter);

    }
}
