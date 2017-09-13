package com.example.vikasdeshpande.inclass02;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Vikas Deshpande on 9/12/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AdapterHolder>
{
    private List<Shopping> listData;
    private LayoutInflater inflater;
    private Context mContext;

    public void setListData(List<Shopping> appslist) {
        this.listData.clear();
        this.listData.addAll(appslist);
    }

    public RecyclerAdapter(List<Shopping> listData, Context c) {
        inflater = LayoutInflater.from(c);
        this.listData = listData;
        mContext = c;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_saved, parent, false);
        return new AdapterHolder(view);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class AdapterHolder extends RecyclerView.ViewHolder{

        private TextView tvProductName;
        private TextView tvDiscount;
        private TextView tvPrice;
        private TextView tvRegion;
        private ImageView ivDisplayImage;
        private View container;



        public AdapterHolder(View itemView) {
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvProductName.setTypeface(null, Typeface.BOLD);
            tvDiscount = (TextView) itemView.findViewById(R.id.tvDiscount);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvRegion = (TextView) itemView.findViewById(R.id.tvRegion);
            ivDisplayImage = (ImageView) itemView.findViewById(R.id.ivDisplayImage);
        }
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, final int position) {

        Shopping shop = listData.get(position);
        holder.tvProductName.setText(shop.getName());
        holder.tvPrice.setText(Double.toString(shop.getPrice()));
        holder.tvRegion.setText(shop.getRegion());
        holder.tvDiscount.setText(Double.toString(shop.getDiscount()));
        Picasso.with(mContext).load("https://advancemad.000webhostapp.com/images/"+shop.getPhoto_name()).into(holder.ivDisplayImage);
}
}
