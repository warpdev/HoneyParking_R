package com.honeyparking.parking.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;


public class search_selc extends AppCompatActivity {

    public static String mApiKey="5c81ed16-0bbb-4121-8542-6784e3e0f249";

    private TMapView		mMapView = null;
    private TMapData        tMapData=new TMapData();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    park_Adapter myAdapter;
    ArrayList<park_info> parkInfoArrayList = new ArrayList<>();

    // 맵 컨트롤러
    // 맵을 추가할 레이아웃
    LinearLayout MapContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_selc);

        mRecyclerView = findViewById(R.id.park_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        myAdapter = new park_Adapter(parkInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
        final TMapMarkerItem tmarker = new TMapMarkerItem();
        Intent input_i=getIntent();
        double point_x=input_i.getExtras().getDouble("p_x");
        double point_y=input_i.getExtras().getDouble("p_y");
        TMapPoint tpoint = new TMapPoint(point_x,point_y);
        MapContainer=(LinearLayout)findViewById(R.id.mapview);
        mMapView = new TMapView(this);
        mMapView.setCenterPoint(point_y,point_x);
        mMapView.setLocationPoint(point_y,point_x);
        tmarker.setTMapPoint(tpoint);
        mMapView.addMarkerItem("mypoint",tmarker);
        tMapData.findAroundNamePOI(tpoint, "주차장", new TMapData.FindAroundNamePOIListenerCallback() {
            @Override
            public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {

                for(int i = 0; i < poiItem.size(); i++) {
                    TMapPOIItem item = poiItem.get(i);
                    Log.d("honeyparking",item.getPOIName());
                    TMapMarkerItem ttttt=new TMapMarkerItem();
                    ttttt.setTMapPoint(item.getPOIPoint());
                    mMapView.addMarkerItem(i+"",ttttt);
                    parkInfoArrayList.add(new park_info(item.getPOIName(),item.parkFlag));
                }
            }
        });
        MapContainer.addView(mMapView);

        configureMapView();
    }

    public void btn_let_pay(View v){
        Intent i=new Intent(search_selc.this,parking_detail.class);
        startActivity(i);
    }
    private void configureMapView() {
        mMapView.setSKTMapApiKey(mApiKey);
    }
}