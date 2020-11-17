package com.example.recipedcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity {

    public static final String TAG = "LoginActivity";
    private ImageView ivLogo;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick sign up button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUpUser(username, password);
            }
        });

    }

//    private void loginUser(String username, String password) {
//        Log.i(TAG, "Attempting to login user " + username);
//        ParseUser.logInInBackground(username, password, new LogInCallback() {
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                if (e != null) {
//                    Log.e(TAG, "Issue with login", e);
//                    Toast.makeText(LoginActivity.this, "Issue with login!", Toast.LENGTH_SHORT).show();
//
//                    return;
//                }
//                goMainActivity();
//                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void signUpUser(String username, String password) {
//        Log.i(TAG, "Attempting to sign up user " + username);
//        // Create the ParseUser
//        ParseUser user = new ParseUser();
//        // Set core properties
//        user.setUsername(username);
//        user.setPassword(password);
//        // Invoke signUpInBackground
//        user.signUpInBackground(new SignUpCallback() {
//            public void done(ParseException e) {
//                if (e == null) {
//                    // Hooray! Let them use the app now.
//                    goMainActivity();
//                    Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Sign up didn't succeed. Look at the ParseException
//                    // to figure out what went wrong
//                    Log.e(TAG, "Issue with signup", e);
//                    Toast.makeText(LoginActivity.this, "Issue with signup!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//        });
//    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
