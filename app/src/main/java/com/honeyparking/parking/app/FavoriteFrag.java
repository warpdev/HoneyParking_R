package com.honeyparking.parking.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFrag extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public FavoriteFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_favorite, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.favbiglist);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<FavInfo> parkInfoArrayList = new ArrayList<>();
        parkInfoArrayList.add(new FavInfo("A 주차장","서울시혜화로 43","주차횟수10회"));
        parkInfoArrayList.add(new FavInfo("A 주차장","서울시혜화로 43","주차횟수10회"));
        parkInfoArrayList.add(new FavInfo("A 주차장","서울시혜화로 43","주차횟수10회"));

        MyAdapterFav myAdapter = new MyAdapterFav(parkInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
        return v;
    }

}
