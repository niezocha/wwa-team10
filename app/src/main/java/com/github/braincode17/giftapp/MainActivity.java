package com.github.braincode17.giftapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.braincode17.giftapp.SearchList.SearchService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    String urlSearch = "http://inspiracje.allegro.pl/api/offers/";
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl(urlSearch).build();

        SearchService searchService = retrofit.create(SearchService.class);
        searchService.search("dlaniej", "200", "random").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();




    }


}
