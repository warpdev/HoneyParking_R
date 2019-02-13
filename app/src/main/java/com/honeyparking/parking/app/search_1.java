package com.honeyparking.parking.app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class search_1 extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_1);
        mRecyclerView = findViewById(R.id.recentlist);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<locateinfo> foodInfoArrayList = new ArrayList<>();
        foodInfoArrayList.add(new locateinfo(R.drawable.icn_location, "검색지 이름"));
        foodInfoArrayList.add(new locateinfo(R.drawable.icn_location, "4,600원"));
        foodInfoArrayList.add(new locateinfo(R.drawable.icn_location, "4,000원"));

        MyAdapter myAdapter = new MyAdapter(foodInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
        TabLayout tabs=findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("최근검색"));
        tabs.addTab(tabs.newTab().setText("즐겨찾기"));



    }
}
