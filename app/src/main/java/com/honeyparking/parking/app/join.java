package com.honeyparking.parking.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class join extends AppCompatActivity {

    private static String IP_ADDRESS = "18.222.46.170";
    private static String TAG="https";

    private SharedPreferences data_origin;
    private SecureSharedPreferences appData;
    private EditText editname;
    private EditText editphone;
    private EditText editid;
    private EditText editpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        editid = findViewById(R.id.editText_joinid);
        editname = findViewById(R.id.editText_name);
        editphone = findViewById(R.id.editText_phone);
        editpwd = findViewById(R.id.editText_joinpw);
        final Button summitjoin=findViewById(R.id.Big_btn);
        Toolbar tb = (Toolbar) findViewById(R.id.app_toolbar) ;
        setSupportActionBar(tb) ;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("회원가입");


        SSLConnect ssl=new SSLConnect();

        ssl.postHttps(TAG+"://"+IP_ADDRESS,1000,1000);
        editid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력하기 전
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editid.getText().toString()!=null && editname.getText().toString()!=null && editphone.getText().toString()!=null && editpwd.getText().toString()!=null){
                    summitjoin.setEnabled(true);
                }
                else{
                    summitjoin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력이 끝났을 때
            }
        });
        editname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력하기 전
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editid.getText().toString()!=null && editname.getText().toString()!=null && editphone.getText().toString()!=null && editpwd.getText().toString()!=null){
                    summitjoin.setEnabled(true);
                }
                else{
                    summitjoin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력이 끝났을 때
            }
        });
        editphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력하기 전
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editid.getText().toString()!=null && editname.getText().toString()!=null && editphone.getText().toString()!=null && editpwd.getText().toString()!=null){
                    summitjoin.setEnabled(true);
                }
                else{
                    summitjoin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력이 끝났을 때
            }
        });
        editpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력하기 전
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editid.getText().toString()!=null && editname.getText().toString()!=null && editphone.getText().toString()!=null && editpwd.getText().toString()!=null){
                    summitjoin.setEnabled(true);
                }
                else{
                    summitjoin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력이 끝났을 때
            }
        });
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
    public void submit_join(View V) {
        editid = findViewById(R.id.editText_joinid);
        editname = findViewById(R.id.editText_name);
        editphone = findViewById(R.id.editText_phone);
        editpwd = findViewById(R.id.editText_joinpw);
        String mName = editname.getText().toString();
        String mPhone = editphone.getText().toString();
        String mId = editid.getText().toString();
        String mPwd = editpwd.getText().toString();
        InsertData task=new InsertData();
        data_origin=getSharedPreferences("appData",MODE_PRIVATE);
        appData=new SecureSharedPreferences(data_origin);
        appData.put("id",mId);
        appData.put("pw",mPwd);
        task.execute("https://"+IP_ADDRESS+"/honeyParking/insert.php",mName,mPhone,mId,mPwd);

    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(join.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
            progressDialog.onDetachedFromWindow();
            Intent i=new Intent(join.this,MainActivity.class);
            startActivity(i);
            finish();
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String) params[1];
            String phone = (String) params[2];
            String id=params[3];
            String pwd=params[4];

            String serverURL = (String) params[0];
            String postParameters = "name=" + name + "&userid=" + id + "&pwd=" + pwd + "&phone_num=" + phone;


            try {

                URL url = new URL(serverURL);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();


                httpsURLConnection.setReadTimeout(5000);
                httpsURLConnection.setConnectTimeout(5000);
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.connect();


                OutputStream outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpsURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);
                //

                InputStream inputStream;
                if (responseStatusCode == HttpsURLConnection.HTTP_OK) {
                    inputStream = httpsURLConnection.getInputStream();
                } else {
                    inputStream = httpsURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {


                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }

        }
    }
}
