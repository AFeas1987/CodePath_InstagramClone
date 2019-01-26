package com.af1987.codepath.instagramclone.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.af1987.codepath.instagramclone.R;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("username", null);
        String password = prefs.getString("password", null);
        if (username != null && password != null) {
            login(username, password);
        }

        btnLogin.setOnClickListener(v -> login(
                etUsername.getText().toString(), etPassword.getText().toString()));
    }

    private void login(String username, String pw) {
        ParseUser.logInInBackground(username, pw, (user, e) -> {
            if (e != null) {
                Log.e("_AF", "login failed: " + e.getMessage());
            }
            else {
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putString("username", username).putString("password", pw).apply();
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
