package com.honeyparking.parking.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
    }
    public void ck_next(View V){
        Intent i = new Intent(this,join.class);
        startActivity(i);
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
