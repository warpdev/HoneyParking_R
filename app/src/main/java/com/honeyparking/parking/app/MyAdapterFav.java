
package com.honeyparking.parking.app;

        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapterFav extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ivPicture;
        TextView tvPrice;
        TextView tvnum;

        MyViewHolder(View view){
            super(view);
            ivPicture = view.findViewById(R.id.parkname);
            tvPrice = view.findViewById(R.id.parkloc);
            tvnum=view.findViewById(R.id.parkcnt);
        }
        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent ittt= new Intent(v.getContext(),payment_result.class);
            ittt.putExtra("type",1);
           // v.getContext().startActivity(ittt);
        }
    }

    private ArrayList<FavInfo> InfoArrayList;
    MyAdapterFav(ArrayList<FavInfo> InfoArrayList){
        this.InfoArrayList = InfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorrow, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.ivPicture.setText(InfoArrayList.get(position).park_name);
        myViewHolder.tvPrice.setText(InfoArrayList.get(position).park_loc);
        myViewHolder.tvnum.setText(InfoArrayList.get(position).park_cnt);
    }

    @Override
    public int getItemCount() {
        return InfoArrayList.size();
    }
}