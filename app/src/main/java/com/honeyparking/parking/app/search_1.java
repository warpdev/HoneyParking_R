package com.honeyparking.parking.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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
    private SearchView mSearchView;
    EditText input_text;
    TMapPOIItem  item;
    ArrayList<locateinfo> foodInfoArrayList = new ArrayList<>();
    MyAdapter myAdapter;
    public static Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_1);

        Toolbar tb = (Toolbar) findViewById(R.id.app_toolbar) ;
        tb.inflateMenu(R.menu.menu);
        setSupportActionBar(tb) ;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Menu menu = tb.getMenu();
        MenuItem item = menu.add("検索");
        item.setIcon(R.drawable.icn_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setActionView(mSearchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });*/
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
        input_text = findViewById(R.id.user_search_input);

        configureMapView();
        input_text.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        input_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_SEARCH){
                    go_search();
                }
                return false;
            }
        });


    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void ck_list(View v){
        Intent it = new Intent(search_1.this,search_selc.class);
        startActivity(it);
    }
    public void go_search(){
        input_text = findViewById(R.id.user_search_input);
        final Context context=search_1.this;
        foodInfoArrayList.clear();

        activity=this;
        tMapData.findAllPOI(input_text.getText().toString(), new TMapData.FindAllPOIListenerCallback() {
            @Override
            public void onFindAllPOI(ArrayList poiItem) {

                for(int i = 0; i < poiItem.size(); i++) {
                    item = (TMapPOIItem) poiItem.get(i);
                    foodInfoArrayList.add(new locateinfo(R.drawable.icn_location,item.getPOIName().toString(),item.getPOIPoint()));
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).run();
                Log.d("honeyparking",":"+foodInfoArrayList.size());
            }
        });
    }

    public void btn_search_icn(View v){
        go_search();
    }
    private void configureMapView() {
        mMapView.setSKTMapApiKey(mApiKey);
    }
    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(input_text.getWindowToken(),0);
    }


}
