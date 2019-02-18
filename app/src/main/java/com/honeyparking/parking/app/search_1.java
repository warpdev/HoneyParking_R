package com.honeyparking.parking.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class search_1 extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static String mApiKey="5c81ed16-0bbb-4121-8542-6784e3e0f249";
    InputMethodManager imm;



    private TMapView mMapView = null;
    private TMapData tMapData=new TMapData();
    EditText input_text;
    TMapPOIItem  item;
    ArrayList<locateinfo> foodInfoArrayList = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_1);
        mRecyclerView = findViewById(R.id.recentlist);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        myAdapter = new MyAdapter(foodInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
        TabLayout tabs=findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("최근검색"));
        tabs.addTab(tabs.newTab().setText("즐겨찾기"));

        mMapView = new TMapView(this);


    }
    public void ck_list(View v){
        Intent it = new Intent(search_1.this,search_selc.class);
        startActivity(it);
    }
    public void btn_search_icn(View v){
        input_text = findViewById(R.id.user_search_input);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        configureMapView();
        foodInfoArrayList.clear();
        tMapData.findAllPOI(input_text.getText().toString(), new TMapData.FindAllPOIListenerCallback() {
            @Override
                public void onFindAllPOI(ArrayList poiItem) {
                    for(int i = 0; i < poiItem.size(); i++) {
                        item = (TMapPOIItem) poiItem.get(i);
                        foodInfoArrayList.add(new locateinfo(R.drawable.icn_location,item.getPOIName().toString(),item.getPOIPoint()));
                    }
                Log.d("honeyparking",":"+foodInfoArrayList.size());
            }
        });
        myAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(myAdapter);
        hideKeyboard();
    }
    private void configureMapView() {
        mMapView.setSKTMapApiKey(mApiKey);
    }
    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(input_text.getWindowToken(),0);
    }


}
