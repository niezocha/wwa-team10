package com.github.braincode17.giftapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.braincode17.giftapp.SearchList.BaseSearchResult;
import com.github.braincode17.giftapp.SearchList.OnItemClick;

import java.util.Collections;
import java.util.List;




public class SerchRusultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<BaseSearchResult> list = Collections.emptyList();
    private OnItemClick onItemClick;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_item_layout, parent, false);
        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseSearchResult currentResult = list.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.price.setText(String.valueOf(currentResult.getItemPrice())+" zł");
        Glide.with(myViewHolder.image.getContext()).load(currentResult.getItemImage()).into(myViewHolder.image);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(list.get(position).getItemId(), list.get(position).getItemImage(), list.get(position).getName(), list.get(position).getItemPrice(),
                        list.get(position).getShippPrice(), list.get(position).getShippTime());

            }
        });
    }

    public void setList(List<BaseSearchResult> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
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
