package com.honeyparking.parking.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.Tmap.TMapPoint;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_STRS = 0 ;
    private static final int ITEM_VIEW_TYPE_IMGS = 1 ;
    private static final int ITEM_VIEW_TYPE_MAX = 2 ;


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivPicture;
        TextView tvPrice;
        TMapPoint tPoint;

        MyViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            ivPicture = view.findViewById(R.id.iv_picture);
            tvPrice = view.findViewById(R.id.name_loc);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent tii=new Intent(v.getContext(),search_selc.class);
            Toast.makeText(context, tPoint +"", Toast.LENGTH_LONG).show();
            tii.putExtra("p_x", tPoint.getLatitude());
            tii.putExtra("p_y", tPoint.getLongitude());
            v.getContext().startActivity(tii);
        }
    }

    private ArrayList<locateinfo> InfoArrayList;
    MyAdapter(ArrayList<locateinfo> InfoArrayList){
        this.InfoArrayList = InfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylrow, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.ivPicture.setImageResource(InfoArrayList.get(position).drawableId);
        myViewHolder.tvPrice.setText(InfoArrayList.get(position).price);
        myViewHolder.tPoint= InfoArrayList.get(position).point;
    }
    @Override
    public int getItemCount() {
        return InfoArrayList.size();
    }

}