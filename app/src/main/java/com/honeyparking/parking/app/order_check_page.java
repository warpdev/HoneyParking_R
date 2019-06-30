package com.honeyparking.parking.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class order_check_page extends AppCompatActivity {

    private static String IP_ADDRESS = "18.222.46.170";
    private static String TAG="https";
    private String data = "";
    private WindowManager.LayoutParams params;
    private float brightness; // 밝기값은 float형으로 저장되어 있습니다.

    private String mId;
    private String mPwd;
    private SharedPreferences data_origin;

    private boolean kk=false;
    private SecureSharedPreferences appData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check_page);


        SSLConnect ssl=new SSLConnect();

        ssl.postHttps(TAG+"://"+IP_ADDRESS,1000,1000);
        Toolbar tb = (Toolbar) findViewById(R.id.app_toolbar) ;
        setSupportActionBar(tb) ;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("주차권");
        params = getWindow().getAttributes();

        InsertData task=new InsertData();
        data_origin=getSharedPreferences("appData",MODE_PRIVATE);
        appData=new SecureSharedPreferences(data_origin);
        mId=appData.get("id","0");
        mPwd=appData.get("pw","0");
        task.execute("https://"+IP_ADDRESS+"/honeyParking/gethoney.php",mId,mPwd);
    }
    @Override protected void onResume() {
        super.onResume();

        // 기존 밝기 저장
        brightness = params.screenBrightness;
        // 최대 밝기로 설정
        params.screenBrightness = 1f;
        // 밝기 설정 적용
        getWindow().setAttributes(params);
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
    @Override protected void onPause() {
        super.onPause();

        // 기존 밝기로 변경
        params.screenBrightness = brightness;
        getWindow().setAttributes(params);
    }
    public void generateRQCode(String contents) {
        ImageView QR_form;
        QR_form=findViewById(R.id.code_form);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            Bitmap bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 700, 700));
            QR_form.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    final Handler handler = new Handler()

    {

        public void handleMessage(Message msg)

        {

            TextView hny=findViewById(R.id.tmpname);
            hny.setText("남은 꿀은 "+data+"꿀 입니다.");
            if(kk) {
                generateRQCode(mId);
            }
        }

    };


    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String) params[1];
            String pwd = (String) params[2];

            String serverURL = (String) params[0];
            String postParameters = "userid=" + id + "&userpwd=" + pwd;


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


                InputStream is = null;
                BufferedReader in = null;
                is = httpsURLConnection.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();

                if (data.equals("0")) {
                    kk = false;
                } else{
                    kk=true;
                }
                new Thread()

                {

                    public void run()

                    {

                        Message msg = handler.obtainMessage();

                        handler.sendMessage(msg);

                    }

                }.start();
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
