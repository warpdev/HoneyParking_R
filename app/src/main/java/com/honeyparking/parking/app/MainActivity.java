package com.honeyparking.parking.app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private SharedPreferences appdataorigin;
    private SecureSharedPreferences appData;

    private static String IP_ADDRESS = "18.222.46.170";
    private static String TAG="https";

    private String sId;
    private String sPw;
    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    int bar_stat = 1;
    ImageView btn_home;
    ImageView btn_fav;
    ImageView btn_sale;
    ImageView btn_my;
    double longitude; //경도
    double latitude;   //위도
    double altitude;   //고도
    float accuracy;    //정확도
    String provider;   //위치제공자

    Intent intent_i;

    String loginId, loginPwd;

    int ckkkk=0;
    LocationManager LM;
    RecyclerView mRecyclerViewF;
    RecyclerView.LayoutManager mLayoutManagerF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent i = new Intent(MainActivity.this,LoginActivity.class);
        //startActivity(i);


        callFragment(FRAGMENT1);
        btn_home = findViewById(R.id.main_home_icn);
        btn_fav = findViewById(R.id.main_fav_icn);
        btn_sale = findViewById(R.id.main_sale_icn);
        btn_my = findViewById(R.id.main_my_icn);

    }

    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no) {
            case 1:
                // '프래그먼트1' 호출
                BlankFragment fragment1 = new BlankFragment();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                FavoriteFrag fragment2 = new FavoriteFrag();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                break;
            case 3:
                sale_frag fragment3 = new sale_frag();
                transaction.replace(R.id.fragment_container, fragment3);
                transaction.commit();
                break;
            case 4:
                my_frag fragment4 = new my_frag();
                transaction.replace(R.id.fragment_container, fragment4);

                transaction.commit();
                break;
        }

    }

    public void go_pay(View v){
        Intent i=new Intent(MainActivity.this,payment_result.class);
        i.putExtra("type",1);
        startActivity(i);
    }
    public void onPause(){
        super.onPause();
        if(ckkkk==1) {
            LM.removeUpdates(mLocationListener);
        }
    }

    public void ck_search_loc(View v) {
        LM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d("honeyparking", "sjb");
        // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){



            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},9801);

        }
        else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},9802);
        }
        else {
            try {
                LM.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                        100, // 통지사이의 최소 시간간격 (miliSecond)
                        1, // 통지사이의 최소 변경거리 (m)
                        mLocationListener);
                LM.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                        100, // 통지사이의 최소 시간간격 (miliSecond)
                        1, // 통지사이의 최소 변경거리 (m)
                        mLocationListener);



            }
            catch (SecurityException e){
                Log.d("honeyparking",e.toString());
            }

        }

    }
    public void onResume(){
        super.onResume();
        ckkkk=0;
    }

    private final LocationListener mLocationListener=new LocationListener(){

        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            longitude = location.getLongitude(); //경도
            latitude=location.getLatitude();
            altitude = location.getAltitude();   //고도
            accuracy = location.getAccuracy();    //정확도
            provider = location.getProvider();   //위치제공자
            Log.d("test", "onLocationChanged, location:" + location);
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            Toast.makeText(MainActivity.this, "위치정보 : " + provider + "\n위도 : " + longitude + "\n경도 : " + latitude
                    + "\n고도 : " + altitude + "\n정확도 : "  + accuracy, Toast.LENGTH_SHORT).show();


            ckkkk=1;
            intent_i=new Intent(MainActivity.this,search_selc.class);
            intent_i.putExtra("p_y",longitude);
            intent_i.putExtra("p_x",latitude);
            startActivity(intent_i);

        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }

    };

    public void ck_search(View v){
        Intent i = new Intent(MainActivity.this,search_1.class);
        startActivity(i);
    }
    public void main_home_btn(View v){
        if(bar_stat!=1){
            bar_stat=1;
            btn_home.setBackgroundResource(R.drawable.main_menu_active_home);
            btn_fav.setBackgroundResource(R.drawable.main_menu_deactive_favorite);
            btn_sale.setBackgroundResource(R.drawable.main_menu_deactive_coupon);
            btn_my.setBackgroundResource(R.drawable.main_menu_deactive_myinfo);
            callFragment(1);
        }
    }

    public void main_fav_btn(View v){
        if(bar_stat!=2){
            bar_stat=2;
            btn_home.setBackgroundResource(R.drawable.main_menu_deactive_home_copy);
            btn_fav.setBackgroundResource(R.drawable.main_menu_active_favorite);
            btn_sale.setBackgroundResource(R.drawable.main_menu_deactive_coupon);
            btn_my.setBackgroundResource(R.drawable.main_menu_deactive_myinfo);
            callFragment(2);
        }
    }

    public void main_sale_btn(View v){
        if(bar_stat!=3){
            bar_stat=3;
            btn_home.setBackgroundResource(R.drawable.main_menu_deactive_home_copy);
            btn_fav.setBackgroundResource(R.drawable.main_menu_deactive_favorite);
            btn_sale.setBackgroundResource(R.drawable.main_menu_active_coupon);
            btn_my.setBackgroundResource(R.drawable.main_menu_deactive_myinfo);
            callFragment(3);
        }
    }
    public void main_my_btn(View v){
        if(bar_stat!=4){
            bar_stat=4;
            btn_home.setBackgroundResource(R.drawable.main_menu_deactive_home_copy);
            btn_fav.setBackgroundResource(R.drawable.main_menu_deactive_favorite);
            btn_sale.setBackgroundResource(R.drawable.main_menu_deactive_coupon);
            btn_my.setBackgroundResource(R.drawable.main_menu_active_myinfo);
            callFragment(4);
        }
    }

    public void btn_setting(View v){
        Intent i= new Intent(MainActivity.this,setting_activity.class);
        startActivity(i);
    }
    public void btn_go_orderck(View v){
        Intent i= new Intent(MainActivity.this,order_check_page.class);
        startActivity(i);
    }
}
