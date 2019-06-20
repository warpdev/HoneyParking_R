package com.honeyparking.parking.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class firstCheck extends Activity {

    private SharedPreferences appdataorigin;
    private SecureSharedPreferences appData;

    private static String IP_ADDRESS = "13.59.15.160";
    private static String TAG="https";

    private String sId;
    private String sPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SSLConnect ssl=new SSLConnect();

        ssl.postHttps(TAG+"://"+IP_ADDRESS,1000,1000);
        appdataorigin=getSharedPreferences("appData",MODE_PRIVATE);
        appData=new SecureSharedPreferences(appdataorigin);

        sId=appData.get("id","0");
        sPw=appData.get("pw","0");
        //loginDB lDB= new loginDB();
        //lDB.execute();

        Intent i=new Intent(firstCheck.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public class loginDB extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "userid=" + sId + "&userpwd="+sPw;
            Log.e("POST",param);
            try {

                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
//특정 hostname만 승인을 해주는 형태
                        if(hostname.equalsIgnoreCase(IP_ADDRESS)) //내가 우회하고자하는 url 주소를 넣어준다.
                            return true;
                        else
                            return false;
                    }
                };
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

                /* 서버연결 */
                URL url = new URL(
                        TAG+"://"+IP_ADDRESS+"/honeyParking/login.php");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                trustAllHosts();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                /* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

                /* 서버 -> 안드로이드 파라메터값 전달 */
                InputStream is = null;
                BufferedReader in = null;
                String data = "";

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ( ( line = in.readLine() ) != null )
                {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();

                /* 서버에서 응답 */
                Log.d("honey",data);
                if(data.equals("0"))
                {
                    Log.d("honey","1111");
                    Intent i=new Intent(firstCheck.this,LoginActivity.class);
                    startActivity(i);
                }
                else if(data.equals("1"))
                {
                    Log.d("honey","2222");
                    Intent i=new Intent(firstCheck.this, MainActivity.class);
                    startActivity(i);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
    private static void trustAllHosts() {

        // Create a trust manager that does not validate certificate chains

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {

                return new java.security.cert.X509Certificate[]{};

            }



            @Override

            public void checkClientTrusted(

                    java.security.cert.X509Certificate[] chain,

                    String authType)

                    throws java.security.cert.CertificateException {

                // TODO Auto-generated method stub



            }



            @Override

            public void checkServerTrusted(

                    java.security.cert.X509Certificate[] chain,

                    String authType)

                    throws java.security.cert.CertificateException {

                // TODO Auto-generated method stub



            }

        }};



        // Install the all-trusting trust manager

        try {

            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection

                    .setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
