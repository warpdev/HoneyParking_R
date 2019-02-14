package com.honeyparking.parking.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    int bar_stat=1;
    ImageView btn_home;
    ImageView btn_fav;
    ImageView btn_sale;
    ImageView btn_my;

    RecyclerView mRecyclerViewF;
    RecyclerView.LayoutManager mLayoutManagerF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent i = new Intent(MainActivity.this,LoginActivity.class);
        //startActivity(i);

        callFragment(FRAGMENT1);
        btn_home=findViewById(R.id.main_home_icn);
        btn_fav=findViewById(R.id.main_fav_icn);
        btn_sale=findViewById(R.id.main_sale_icn);
        btn_my=findViewById(R.id.main_my_icn);
    }
    private void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no){
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
            case 4:
                my_frag fragment4 = new my_frag();
                transaction.replace(R.id.fragment_container, fragment4);
                transaction.commit();
                break;
        }

    }

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
}
