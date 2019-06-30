package com.honeyparking.parking.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.TMapData.TMapPathType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.ParserConfigurationException;

public class search_selc extends AppCompatActivity {

    public static String mApiKey = "5c81ed16-0bbb-4121-8542-6784e3e0f249";


    private static String IP_ADDRESS = "18.222.46.170";
    private static String TAG = "https";

    private static double PED_SPEED=0.97444634;


    private String mJsonString;


    private TMapView mMapView = null;
    private TMapData tMapData = new TMapData();
    private TMapPoint tstart_point;
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

        SSLConnect ssl=new SSLConnect();
        ssl.postHttps(IP_ADDRESS,1000,1000);
        mRecyclerView = findViewById(R.id.park_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar tb = (Toolbar) findViewById(R.id.app_toolbar) ;
        setSupportActionBar(tb) ;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAdapter = new park_Adapter(parkInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
        MapContainer=(LinearLayout)findViewById(R.id.mapview);
        mMapView = new TMapView(this);

        mMapView.setHttpsMode(true);
        disableSSLCertificateChecking();
        Intent input_i=getIntent();
        double point_x=input_i.getExtras().getDouble("p_x");
        double point_y=input_i.getExtras().getDouble("p_y");
        tstart_point=new TMapPoint(point_x,point_y);

        Log.e("꿀주차",Double.toString(point_x));

        GetData task = new GetData();
        task.execute("https://" + IP_ADDRESS + "/honeyParking/getjson.php", "");

        myAdapter.notifyDataSetChanged();




        MapContainer.addView(mMapView);





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

    private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // not implemented
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // not implemented
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                }
        };

        try {

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }

            });
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(search_selc.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null){

            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();


                httpsURLConnection.setReadTimeout(5000);
                httpsURLConnection.setConnectTimeout(5000);
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.connect();


                OutputStream outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpsURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpsURLConnection.HTTP_OK) {
                    inputStream = httpsURLConnection.getInputStream();
                }
                else{
                    inputStream = httpsURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }
    public void back_search(View v){
        finish();
    }


    private void showResult(){

        String TAG_JSON="webnautes";
        String TAG_ID = "name";
        String TAG_NAME = "point_x";
        String TAG_NAME_2 = "point_y";
        String TAG_COUNTRY ="isfull";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String name2=item.getString(TAG_NAME_2);
                String country = item.getString(TAG_COUNTRY);

                final park_info personalData = new park_info();

                personalData.setPark_name_s(id);
                personalData.setPark_lo_s(name);
                personalData.setPark_lo_y(name2);
                personalData.setPark_can(country);

                TMapMarkerItem tmarker = new TMapMarkerItem();
                double point_x=Double.parseDouble(name);
                double point_y=Double.parseDouble(name2);

                TMapPoint tpoint = new TMapPoint(point_x,point_y);


                tMapData.findPathDataWithType(TMapPathType.PEDESTRIAN_PATH, tpoint, tstart_point, new TMapData.FindPathDataListenerCallback() {
                    @Override
                    public void onFindPathData(TMapPolyLine polyLine) {
                        polyLine.setLineColor(Color.BLUE);
                        mMapView.addTMapPath(polyLine);
                        double tdist= polyLine.getDistance();
                        personalData.setPark_dist((int)(tdist/PED_SPEED));
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

                    }
                });

                mMapView.setCenterPoint(point_y,point_x);
                mMapView.setLocationPoint(point_y,point_x);
                tmarker.setTMapPoint(tpoint);
                mMapView.addMarkerItem(i+"1",tmarker);



                parkInfoArrayList.add(personalData);
            }

            //MapContainer.addView(mMapView);
            configureMapView();
            myAdapter.notifyDataSetChanged();


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


    public void btn_let_pay(View v){
    }
    private void configureMapView() {
        mMapView.setSKTMapApiKey(mApiKey);
    }
}