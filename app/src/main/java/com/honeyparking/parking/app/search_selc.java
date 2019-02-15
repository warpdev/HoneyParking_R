package com.honeyparking.parking.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;


public class search_selc extends AppCompatActivity {

    private NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID = "YOUR_CLIENT_ID";// 애플리케이션 클라이언트 아이디 값
    // 맵 컨트롤러
    NMapController mMapController = null;
    // 맵을 추가할 레이아웃
    LinearLayout MapContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_selc);
        MapContainer=(LinearLayout)findViewById(R.id.mapview);
        mMapView = new NMapView(this);
        mMapController=mMapView.getMapController();
        mMapView.setClientId(CLIENT_ID);
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        MapContainer.addView(mMapView);
    }
}
