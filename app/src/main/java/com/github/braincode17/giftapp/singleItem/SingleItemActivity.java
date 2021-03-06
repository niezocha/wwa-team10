package com.github.braincode17.giftapp.singleItem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.braincode17.giftapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SingleItemActivity extends AppCompatActivity {


    @BindView(R.id.item_imageview)
    ImageView itemViewImage;

    @BindView(R.id.item_title)
    TextView itemTitle;

    @BindView(R.id.item_price)
    TextView itemPrice;

    @BindView(R.id.item_shippingtime)
    TextView itemShippingTime;

    @BindView(R.id.allegro_button)
    Button allegroButton;

    @BindView(R.id.send_button)
    Button sendButton;


    private String itemId;
    private String allegroItemUrl;
    private String itemName;



    private static final String ID_KEY = "id_key";
    private static final String IMAGE_KEY = "image_key";
    private static final String TITLE_KEY = "title_key";
    private static final String PRICE_KEY = "price_key";
    private static final String SHIPP_TIME_KEY = "shipping_time_key";
    private static final String SHIPP_PRICE_KEY = "shipping_price_key";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ButterKnife.bind(this);
        String imageString = getIntent().getStringExtra(IMAGE_KEY);
        itemId = getIntent().getStringExtra(ID_KEY);
        itemName = getIntent().getStringExtra(TITLE_KEY);

        Glide.with(itemViewImage.getContext()).load(imageString).into(itemViewImage);
        itemTitle.setText(getIntent().getStringExtra(TITLE_KEY));
        itemPrice.setText(getIntent().getStringExtra(PRICE_KEY) + " zł");

        if(getIntent().getStringExtra(SHIPP_TIME_KEY).equals("0")){
            itemShippingTime.setText("od ręki");
        }
        else if(getIntent().getStringExtra(SHIPP_TIME_KEY).equals("1")){
            itemShippingTime.setText("1 dzień");
        }
        else{
            itemShippingTime.setText(getIntent().getStringExtra(SHIPP_TIME_KEY) + " dni");
        }
        allegroItemUrl = "http://allegro.pl/" + itemName + "-i" + itemId + ".html";
        Log.d("adres", allegroItemUrl);


        allegroButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(allegroItemUrl));
            String chooserTitle = getString(R.string.chooser);
            Intent chosenIntent = Intent.createChooser(newIntent, chooserTitle);
            startActivity(chosenIntent);

        });

        sendButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(Intent.ACTION_SEND);
            newIntent.setType("text/plain");
            newIntent.putExtra(Intent.EXTRA_TEXT, "AllePomysł na prezent! \n" + allegroItemUrl);
            startActivity(Intent.createChooser(newIntent, "Send Email"));

        });
    }

    public static Intent createIntent(Context context, String id, String image, String title, String price, String shippPirce, String shippTime) {
        Intent intent = new Intent(context, SingleItemActivity.class);
        intent.putExtra(ID_KEY, id);
        intent.putExtra(IMAGE_KEY, image);
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(PRICE_KEY, price);
        intent.putExtra(SHIPP_PRICE_KEY, shippPirce);
        intent.putExtra(SHIPP_TIME_KEY, shippTime);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
