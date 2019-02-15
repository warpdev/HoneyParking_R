package com.honeyparking.parking.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;



public class search_selc extends AppCompatActivity {

    private final String CLIENT_ID = "YOUR_CLIENT_ID";// 애플리케이션 클라이언트 아이디 값
    // 맵 컨트롤러
    // 맵을 추가할 레이아웃
    LinearLayout MapContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_selc);
        MapContainer=(LinearLayout)findViewById(R.id.mapview);
    }
    public void btn_let_pay(View v){
        Intent i=new Intent(search_selc.this,parking_detail.class);
        startActivity(i);
    }
}
