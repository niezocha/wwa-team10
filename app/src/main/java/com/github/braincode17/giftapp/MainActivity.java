package com.github.braincode17.giftapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnItemClick{


    private String tag = "allegropl";
    private String price = "0";
    private String sort = "random";
    private String defUrl = "https://inspiracje.allegro.pl/api/";
    private String urlSearch = "http://inspiracje.allegro.pl/api/offers/";
    private Retrofit retrofit;

    private static final String[] tags = {"inspirującego","dla niej", "dla niego", "dla dziecka", "dla domu", "z prezentów"};
    private static final String [] tagsVal = {"allegropl", "dlaniej", "dlaniego", "dziecko", "dom", "gadzet"};

    private static final String[] prices = {"dowolnej kwoty", "do 50 zł", "do 100 zł", "do 200 zł"};
    private static final String[] pricesVal = {"0", "50", "100", "200"};

    private static final String[] sorting = {"losowo", "najtańszych", "najdroższych", "najpopularniejszych"};
    private static final String[] sortingVal = {"random", "cheapest", "most_expencive", "most_popular"};

    Map<String, String> tagsMap;
    Map<String, String> pricesMap;
    Map<String, String> sortingsMap;

    @BindView(R.id.view_pager)
    ViewPager viewPager;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.spinner)
    Spinner spinner;

    List<BaseSearchResult> itemsList;

    private LinearLayoutManager layoutManager;
    private SerchRusultAdapter serchRusultAdapter;

    private String tagSelected;
    private String priceSelected;
    private String sortSelected;


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

        updatedSearch();

        tabLayout.setupWithViewPager(viewPager);
        itemsList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        serchRusultAdapter = new SerchRusultAdapter();
        serchRusultAdapter.setOnItemClick(this);
        recyclerView.setAdapter(serchRusultAdapter);
        updatedSearch("dlaniej", "200", "random");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        adapter = new ItemsPagerAdapter(itemsList, sharedPreferences);
        viewPager.setAdapter(adapter);


        tagsMap = generateMap(tags, tagsVal);
        pricesMap = generateMap(prices, pricesVal);
        sortingsMap = generateMap(sorting, sortingVal);

    }

    private void updatedSearch(String tag, String price, String sort) {

    private void updatedSearch() {
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

        if(item.getItemId() == R.id.search_tag){
            setSpinner(tags);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                private String tagSelected;
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tagSelected = spinner.getItemAtPosition(position).toString();
                    if(!tagSelected.equals("inspirującego")){
                        spinner.setVisibility(View.GONE);
                        setTagSelected(tagSelected);
                        updatedSearch();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        else if(item.getItemId() == R.id.search_price){
            Log.d("menu_item", "wybór ceny");
            setSpinner(prices);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                private String priceSelected;
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    priceSelected = spinner.getItemAtPosition(position).toString();
                    if(!priceSelected.equals("dowolnej kwoty")){
                        spinner.setVisibility(View.GONE);
                        setPriceSelected(priceSelected);
                        updatedSearch();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        else if(item.getItemId() == R.id.search_sorting){
            Log.d("menu_item", "wybór kolejności");
            setSpinner(sorting);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                private String sortSelected;
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sortSelected = spinner.getItemAtPosition(position).toString();
                    if(!sortSelected.equals("losowo")){
                        spinner.setVisibility(View.GONE);
                        setSortSelected(sortSelected);
                        updatedSearch();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        else if(item.getItemId() == R.id.search_clear){
            setTagSelected("inspirującego");
            setPriceSelected("dowolnej kwoty");
            setSortSelected("losowo");
            spinner.setVisibility(View.GONE);
            updatedSearch();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMovieItemClick(String id, String url, String title, String price, String shippPirce, String shippTime) {
        Intent intent = SingleItemActivity.createIntent(this, id, url, title, price, shippPirce, shippTime);
        startActivity(intent);
    }


    private void setSpinner(String[] objects) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, objects);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);
    }

    public Map<String, String> generateMap(String[] list1, String[] list2){
        Map<String, String> map = new HashMap<>();
        for (int i=0; i<list1.length; i++){
            map.put(list1[i], list2[i]);
        }
        return map;
    }

}
