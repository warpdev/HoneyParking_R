package com.honeyparking.parking.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "18.222.46.170";
    private static String TAG="https";

    private String sId;
    private String sPw;
    EditText id_form;
    EditText pw_form;

    AlertDialog.Builder alertBuilde;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form

        alertBuilde=new AlertDialog.Builder(this);

        pw_form=findViewById(R.id.editText3);
        id_form=findViewById(R.id.editText2);
        id_form.setFilters(new InputFilter[]{filterAlphaNum});
        pw_form.setFilters(new InputFilter[]{filterPW});
    }
    public void ck_login(View v){
        sId=id_form.getText().toString();
        sPw=pw_form.getText().toString();
        loginDB lDB= new loginDB();
        lDB.execute();
    }

    public class loginDB extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... unused) {

            /* 인풋 파라메터값 생성 */
            String param = "userid=" + sId + "&userpwd="+sPw;
            Log.e("POST",param);
            try {
                /* 서버연결 */
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
                URL url = new URL(
                        TAG+"://"+IP_ADDRESS+"/honeyParking/login.php");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
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

                if(data.equals("0"))
                {

                }
                else if(data.equals("1"))
                {
                    SharedPreferences tdata=getSharedPreferences("appData",MODE_PRIVATE);
                    SecureSharedPreferences appdata=new SecureSharedPreferences(tdata);
                    appdata.put("id",sId);
                    appdata.put("pw",sPw);
                    Intent i=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
    public void ck_sign(View v){
        Intent i= new Intent(LoginActivity.this, agree_page.class);
        startActivity(i);
    }

    public InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };
    public InputFilter filterPW = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9!@#$*()]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };
}

