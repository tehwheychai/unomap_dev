package com.example.wuikhong.unomap;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.wuikhong.unomap.camera.Card;

import java.util.List;

/**
 * Created by Guest on 11/20/2017.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder>  {

    private List<Card> cardList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRank, tvCard, tvSinger, tvYear;
        public ImageView ivAlbum;

        public ViewHolder(View v) {
            super(v);
            tvRank = (TextView) v.findViewById(R.id.tvRank);
            tvCard = (TextView) v.findViewById(R.id.cardName);
            tvSinger = (TextView) v.findViewById(R.id.tvSinger);
            tvYear = (TextView) v.findViewById(R.id.tvYear);
            ivAlbum = (ImageView) v.findViewById(R.id.ivAlbum);


        }

    }

    public CardViewAdapter(List<Card> cardList) {
        this.cardList = cardList;
    }

    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardViewAdapter.ViewHolder holder,int position){
        Card card=cardList.get(position);
        holder.tvRank.setText(String.valueOf(card.getRank()));
        holder.tvCard.setText(String.valueOf(card.getName()));
        holder.tvSinger.setText(String.valueOf(card.getSinger()));
        holder.tvYear.setText(String.valueOf(card.getYear()));
        holder.ivAlbum.setImageResource(card.getPic());
        holder.tvYear.setText("2017");


    }

    @Override
    public int getItemCount(){
        return cardList.size();
    }

}