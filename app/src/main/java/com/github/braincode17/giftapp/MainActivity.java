package com.github.braincode17.giftapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.braincode17.giftapp.SearchList.SearchService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    String urlSearch = "http://inspiracje.allegro.pl/api/offers/";
    private Retrofit retrofit;

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

        retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl(urlSearch).build();

        SearchService searchService = retrofit.create(SearchService.class);
        searchService.search("dlaniej", "200", "random").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();

        tabLayout.setupWithViewPager(viewPager);
        itemsList = new ArrayList<>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ItemsPagerAdapter adapter = new ItemsPagerAdapter(itemsList, sharedPreferences);
        viewPager.setAdapter(adapter);

    }


}
