package com.zskisa.tourismkkc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zskisa.tourismkkc.apimodel.ApiRegister;
import com.zskisa.tourismkkc.apimodel.ApiStatus;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {

    private String p_Login = "LOGIN";
    private SharedPreferences.Editor editor;
    private String fname, lname, password, email;

    @InjectView(R.id.input_fname)
    EditText _fnameText;
    @InjectView(R.id.input_lname)
    EditText _lnameText;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        String PREF_APP = "PREF_APP";
        SharedPreferences sp = getSharedPreferences(PREF_APP, MODE_PRIVATE);
        editor = sp.edit();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                editor.putBoolean(p_Login, false);
                editor.commit();
                finish();
            }
        });
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);


        fname = _fnameText.getText().toString();
        lname = _lnameText.getText().toString();
        password = _passwordText.getText().toString();
        email = _emailText.getText().toString();

        // TODO: Implement your own signup logic here.

        ApiRegister register = new ApiRegister(email, password, fname, lname);
        SignupActivity.Connect connect = new Connect();
        connect.execute(register);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();

                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        editor.putBoolean(p_Login, true);

        /*
        * เก็บข้อมูล name, email เพื่อไว้แสดงผลบนเมนูทางซ้าย
        * */
        //ค่าที่ต้องใช้สำหรับสร้าง ApiLogin
        editor.putString("userEmail", email);
        editor.putString("userPassword", password);
        editor.putString("userFname", fname);
        editor.putString("userLname", lname);
        editor.putString("fbID", "");

        editor.putString("title_name", fname + " " + lname);
        editor.putString("title_email", email);
        editor.commit();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
        editor.putBoolean(p_Login, false);
        editor.commit();
    }

    public boolean validate() {
        boolean valid = true;

        String fname = _fnameText.getText().toString();
        String lname = _lnameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (fname.isEmpty() || fname.length() < 3) {
            _fnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _fnameText.setError(null);
        }

        if (lname.isEmpty() || lname.length() < 3) {
            _lnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _lnameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    class Connect extends AsyncTask<ApiRegister, Void, ApiStatus> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SignupActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
        }

        @Override
        protected ApiStatus doInBackground(ApiRegister... params) {
            return MainActivity.api.register(params[0]);
        }

        @Override
        protected void onPostExecute(ApiStatus apiStatus) {
            super.onPostExecute(apiStatus);
            progressDialog.dismiss();
            if (apiStatus.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "สมัครสมาชิกสำเร็จ", Toast.LENGTH_LONG).show();
                onSignupSuccess();
            } else {
                _signupButton.setEnabled(true);
                Toast.makeText(getApplicationContext(), "ผิดพลาด", Toast.LENGTH_LONG).show();
            }
        }

    }
}
