package com.honeyparking.parking.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class agree_page extends AppCompatActivity {

    private CheckBox ck_all;
    private CheckBox ck1;
    private CheckBox ck2;
    private CheckBox ck3;
    private CheckBox ck4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_page);
        Toolbar tb = (Toolbar) findViewById(R.id.app_toolbar) ;
        setSupportActionBar(tb) ;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("약관동의");
    }
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
    public void ck_next(View V){
        Intent i = new Intent(this,join.class);
        startActivity(i);
    }

    public void btn_en(View V){

        ck_all= findViewById(R.id.checkBox1);
        ck1=findViewById(R.id.checkBox2);
        ck2=findViewById(R.id.checkBox3);
        ck3=findViewById(R.id.checkBox4);
        ck4=findViewById(R.id.checkBox5);

        ck_all.setChecked((ck1.isChecked()&ck2.isChecked()&ck3.isChecked()&ck4.isChecked()));

        boolean t=ck_all.isChecked();
        Button btn= findViewById(R.id.Big_btn);
        btn.setEnabled(t);
        if(t){
            int tcolor= ContextCompat.getColor(this,R.color.btn_able_txt);
            btn.setTextColor(tcolor);
        }
        else{
            btn.setTextColor(Color.WHITE);
        }
    }
    public void all_ck(View V){

        ck_all= findViewById(R.id.checkBox1);
        ck1=findViewById(R.id.checkBox2);
        ck2=findViewById(R.id.checkBox3);
        ck3=findViewById(R.id.checkBox4);
        ck4=findViewById(R.id.checkBox5);
        boolean t=ck_all.isChecked();
        ck1.setChecked(t);
        ck2.setChecked(t);
        ck3.setChecked(t);
        ck4.setChecked(t);
        Button btn= findViewById(R.id.Big_btn);
        btn.setEnabled(t);
        if(t){
            int tcolor= ContextCompat.getColor(this,R.color.btn_able_txt);
            btn.setTextColor(tcolor);
        }
        else{
            btn.setTextColor(Color.WHITE);
        }
    }
}
