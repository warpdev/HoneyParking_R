package com.honeyparking.parking.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "13.59.15.160";
    private static String TAG="http";
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form

        EditText id_form;
        EditText pw_form;
        pw_form=findViewById(R.id.editText3);
        id_form=findViewById(R.id.editText2);
        id_form.setFilters(new InputFilter[]{filterAlphaNum});
        pw_form.setFilters(new InputFilter[]{filterPW});
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

