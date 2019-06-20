package com.honeyparking.parking.app;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class park_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_STRS = 0 ;
    private static final int ITEM_VIEW_TYPE_IMGS = 1 ;
    private static final int ITEM_VIEW_TYPE_MAX = 2 ;


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView park_name;
        TextView park_dist;
        TextView park_detail;

        MyViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            park_name = view.findViewById(R.id.park_name_1);
            park_dist = view.findViewById(R.id.park_detail_2);
            park_detail = view.findViewById(R.id.park_detail_1);
        }

        @Override
        public void onClick(View v) {

            Context context = v.getContext();
            Intent tii=new Intent(v.getContext(),parking_detail.class);
            v.getContext().startActivity(tii);
        }
    }

    private ArrayList<park_info> InfoArrayList;
    park_Adapter(ArrayList<park_info> InfoArrayList){
        this.InfoArrayList = InfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.parkrow, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        int tdist=InfoArrayList.get(position).park_dist;
        int dist_hour=0,dist_min=0,dist_sec=0;

        String dist_s="";

        myViewHolder.park_name.setText(InfoArrayList.get(position).park_name_s);

        if(tdist>=3600){
            dist_hour=tdist/3600;
            tdist%=3600;
            dist_s=dist_s+dist_hour+"시간 ";
        }
        if(tdist>=60){
            dist_min=tdist/60;
            tdist%=60;
            dist_s=dist_s+dist_min+"분 ";
        }
        dist_sec=tdist;
        dist_s=dist_s+dist_sec+"대";


        myViewHolder.park_dist.setText(dist_s);
        Log.d("honeyparking", "가나다"+InfoArrayList.get(position).park_can);
        if(InfoArrayList.get(position).park_can.equalsIgnoreCase("1")){
            myViewHolder.park_detail.setText("주차가능");
        }
        else {
            myViewHolder.park_detail.setText("주차불가");
        }
    }
    @Override
    public int getItemCount() {
        return InfoArrayList.size();
    }

}