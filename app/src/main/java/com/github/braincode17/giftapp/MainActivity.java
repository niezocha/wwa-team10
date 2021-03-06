package com.github.braincode17.giftapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.braincode17.giftapp.SearchList.BaseSearchResult;
import com.github.braincode17.giftapp.SearchList.OnItemClick;
import com.github.braincode17.giftapp.SearchList.SearchService;
import com.github.braincode17.giftapp.singleItem.SingleItemActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements OnItemClick {

    private QueryGenerator queryGenerator = new QueryGenerator();
    private Retrofit retrofit;
    private LinearLayoutManager layoutManager;
    private SerchRusultAdapter serchRusultAdapter;
    private int queryCounter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.spinner_tag)
    Spinner spinnerTag;

    @BindView(R.id.spinner_price)
    Spinner spinnerPrice;

    @BindView(R.id.spinner_sort)
    Spinner spinnerSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_gift);
        queryCounter = 0;

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(queryGenerator.getUrlSearch()).build();

        updatedSearch();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        serchRusultAdapter = new SerchRusultAdapter();
        serchRusultAdapter.setOnItemClick(this);
        recyclerView.setAdapter(serchRusultAdapter);

        setSpinner(queryGenerator.getTags(), spinnerTag);
        setSpinner(queryGenerator.getPrices(), spinnerPrice);
        setSpinner(queryGenerator.getSorting(), spinnerSort);

        spinnerTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tagSelected = spinnerTag.getItemAtPosition(position).toString();
                queryGenerator.setTagSelected(tagSelected);
                queryCounter = 0;
                updatedSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String priceSelected = spinnerPrice.getItemAtPosition(position).toString();
                queryGenerator.setPriceSelected(priceSelected);
                queryCounter = 0;
                updatedSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sortSelected = spinnerSort.getItemAtPosition(position).toString();
                queryGenerator.setSortSelected(sortSelected);
                queryCounter = 0;
                updatedSearch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updatedSearch() {
        queryGenerator.updateQuery();
        SearchService searchService = retrofit.create(SearchService.class);
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(2);

        searchService.search(queryGenerator.getTag(), queryGenerator.getPrice(), queryGenerator.getSort(), String.valueOf(queryCounter))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::fromIterable)
                .map(singleSearchResult -> new BaseSearchResult(singleSearchResult.getItemId(), singleSearchResult.getGalleryImage().getUrl(), singleSearchResult.getName(),
                        String.valueOf(format.format(singleSearchResult.getPrice())), String.valueOf(singleSearchResult.getDeliveryCost()), String.valueOf(singleSearchResult.getDeliveryTime())))
                .toList()
                .subscribe(this::success, this::error
                );
    }

    private void error(Throwable throwable) {
        Toast.makeText(this, R.string.no_results, Toast.LENGTH_LONG).show();
    }

    private void success(List<BaseSearchResult> singleSearchResults) {
        serchRusultAdapter.setList(singleSearchResults);
    }

    @Override
    public void onItemClick(String id, String url, String title, String price, String shippPirce, String shippTime) {
        Intent intent = SingleItemActivity.createIntent(this, id, url, title, price, shippPirce, shippTime);
        startActivity(intent);
    }

    private void setSpinner(String[] objects, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, objects);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            queryCounter += 10;
            Log.d("counter", String.valueOf(queryCounter));
            updatedSearch();
        }
        return super.onOptionsItemSelected(item);
    }
}
