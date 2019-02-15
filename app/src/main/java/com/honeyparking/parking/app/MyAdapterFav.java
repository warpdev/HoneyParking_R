
package com.honeyparking.parking.app;

        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.ArrayList;

public class MyAdapterFav extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ivPicture;
        TextView tvPrice;
        TextView tvnum;

        MyViewHolder(View view){
            super(view);
            ivPicture = view.findViewById(R.id.parkname);
            tvPrice = view.findViewById(R.id.parkloc);
            tvnum=view.findViewById(R.id.parkcnt);
        }
    }

    private ArrayList<locateinfo> InfoArrayList;
    MyAdapterFav(ArrayList<locateinfo> InfoArrayList){
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

        myViewHolder.ivPicture.setText(InfoArrayList.get(position).price);
        myViewHolder.tvPrice.setText(InfoArrayList.get(position).price);
    }

    @Override
    public int getItemCount() {
        return InfoArrayList.size();
    }
}