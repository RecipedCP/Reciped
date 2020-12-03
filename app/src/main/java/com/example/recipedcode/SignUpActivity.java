package com.example.recipedcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    private ImageView ivLogo;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPassword2;
    private Button btnSignup;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword);
        ivLogo = findViewById(R.id.ivLogo);
        btnSignup = findViewById(R.id.btnSignup);
        btnBack = findViewById(R.id.btnBack);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick sign in button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String password2 = etPassword2.getText().toString();
                signUpUser(username, password, password2);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick back button");
                goWelcomeActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goWelcomeActivity() {
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
        finish();
    }

    private void signUpUser(String us, String pw, String pw2)
    {
        ParseUser user = new ParseUser();
        user.setUsername(us);
        if (pw.equals(pw2)){
            user.setPassword(pw);
        }
        else
        {
            return; 
        }

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null)
                {
                    goMainActivity();
                    Toast.makeText(SignUpActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.e(TAG, "Issue with signup", e);
                    Toast.makeText(SignUpActivity.this, "Issue with signup!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


    }
}



// make a go-back to previous page button.
