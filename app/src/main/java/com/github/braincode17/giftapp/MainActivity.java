package com.github.braincode17.giftapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.braincode17.giftapp.SearchList.BaseSearchResult;
import com.github.braincode17.giftapp.SearchList.OnItemClick;
import com.github.braincode17.giftapp.SearchList.SearchService;
import com.github.braincode17.giftapp.singleItem.SingleItemActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnItemClick{


    String urlSearch = "http://inspiracje.allegro.pl/api/offers/";
    private Retrofit retrofit;



    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    List<BaseSearchResult> itemsList;

    private LinearLayoutManager layoutManager;
    private SerchRusultAdapter serchRusultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(urlSearch).build();

        itemsList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        serchRusultAdapter = new SerchRusultAdapter();
        serchRusultAdapter.setOnItemClick(this);
        recyclerView.setAdapter(serchRusultAdapter);
        updatedSearch("dlaniej", "200", "random");
    }

    private void updatedSearch(String tag, String price, String sort) {
        SearchService searchService = retrofit.create(SearchService.class);
        searchService.search(tag, price, sort)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::fromIterable)
                .map(singleSearchResult -> new BaseSearchResult(singleSearchResult.getId(), singleSearchResult.getGalleryImage().getUrl(), singleSearchResult.getName(),
                        String.valueOf(singleSearchResult.getPrice()), String.valueOf(singleSearchResult.getDeliveryCost()), String.valueOf(singleSearchResult.getDaliveryTime())))
                .toList()
                .subscribe(this::success, this::error
                );
    }

    private void error(Throwable throwable) {

    }

    private void success(List<BaseSearchResult> singleSearchResults) {
        serchRusultAdapter.setList(singleSearchResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        } else if (item.getItemId() == R.id.search_price) {
            Log.d("menu_item", "wybór ceny");
        } else if (item.getItemId() == R.id.search_listing) {
            Log.d("menu_item", "wybór kolejności");
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMovieItemClick(String id, String url, String title, String price, String shippPirce, String shippTime) {
        Intent intent = SingleItemActivity.createIntent(this, id, url, title, price, shippPirce, shippTime);
        startActivity(intent);
    }

}
