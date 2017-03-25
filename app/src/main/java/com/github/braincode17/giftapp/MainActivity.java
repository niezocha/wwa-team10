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

import com.github.braincode17.giftapp.SearchList.BaseSearchResult;
import com.github.braincode17.giftapp.SearchList.OnItemClick;
import com.github.braincode17.giftapp.SearchList.SearchService;
import com.github.braincode17.giftapp.singleItem.SingleItemActivity;

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

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.spinner)
    Spinner spinner;

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
                .baseUrl(queryGenerator.getUrlSearch()).build();

        updatedSearch();

        itemsList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        serchRusultAdapter = new SerchRusultAdapter();
        serchRusultAdapter.setOnItemClick(this);
        recyclerView.setAdapter(serchRusultAdapter);

    }

    private void updatedSearch() {
        queryGenerator.updateQuery();
        SearchService searchService = retrofit.create(SearchService.class);
        searchService.search(queryGenerator.getTag(), queryGenerator.getPrice(), queryGenerator.getSort())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::fromIterable)
                .map(singleSearchResult -> new BaseSearchResult(singleSearchResult.getItemId(), singleSearchResult.getGalleryImage().getUrl(), singleSearchResult.getName(),
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

        if (item.getItemId() == R.id.search_tag) {
            setSpinner(queryGenerator.getTags());
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                private String tagSelected;

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tagSelected = spinner.getItemAtPosition(position).toString();
                    if (!tagSelected.equals("inspirującego")) {
                        spinner.setVisibility(View.GONE);
                        queryGenerator.setTagSelected(tagSelected);
                        updatedSearch();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else if (item.getItemId() == R.id.search_price) {
            Log.d("menu_item", "wybór ceny");
            setSpinner(queryGenerator.getPrices());
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                private String priceSelected;

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    priceSelected = spinner.getItemAtPosition(position).toString();
                    if (!priceSelected.equals("dowolnej kwoty")) {
                        spinner.setVisibility(View.GONE);
                        queryGenerator.setPriceSelected(priceSelected);
                        updatedSearch();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else if (item.getItemId() == R.id.search_sorting) {
            Log.d("menu_item", "wybór kolejności");
            setSpinner(queryGenerator.getSorting());
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                private String sortSelected;

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sortSelected = spinner.getItemAtPosition(position).toString();
                    if (!sortSelected.equals("losowo")) {
                        spinner.setVisibility(View.GONE);
                        queryGenerator.setSortSelected(sortSelected);
                        updatedSearch();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else if (item.getItemId() == R.id.search_clear) {
            queryGenerator.clearQuery();
            spinner.setVisibility(View.GONE);
            updatedSearch();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(String id, String url, String title, String price, String shippPirce, String shippTime) {
        Intent intent = SingleItemActivity.createIntent(this, id, url, title, price, shippPirce, shippTime);
        startActivity(intent);
    }

    private void setSpinner(String[] objects) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, objects);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);
    }

}
