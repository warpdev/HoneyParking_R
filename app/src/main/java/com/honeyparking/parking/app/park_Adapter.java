package com.honeyparking.parking.app;

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
        TextView park_detail;

        MyViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            park_name = view.findViewById(R.id.park_name_1);
            park_detail = view.findViewById(R.id.park_detail_1);
        }

        @Override
        public void onClick(View v) {
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
        myViewHolder.park_name.setText(InfoArrayList.get(position).park_name_s);
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