package com.github.braincode17.giftapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.braincode17.giftapp.SearchList.BaseSearchResult;

import java.util.Collections;
import java.util.List;

import static butterknife.ButterKnife.findById;


public class SerchRusultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<BaseSearchResult> list = Collections.emptyList();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_item_layout, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseSearchResult currentResult = list.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.price.setText(currentResult.getItemPrice());
        Glide.with(myViewHolder.image.getContext()).load(currentResult.getItemImage()).into(myViewHolder.image);
        Log.d("result im", currentResult.getItemImage());
        Log.d("result t", currentResult.getItemPrice());

    }

    public void setList(List<BaseSearchResult> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView price;


        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.listing_image);
            price = (TextView) itemView.findViewById(R.id.listing_price);
        }
    }
}
