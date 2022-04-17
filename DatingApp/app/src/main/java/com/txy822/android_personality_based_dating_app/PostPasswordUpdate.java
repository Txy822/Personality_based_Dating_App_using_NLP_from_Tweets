package com.txy822.android_personality_based_dating_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


import android.content.Intent;
import android.view.View;

public class PostPasswordUpdate extends AppCompatActivity {
    private final String TAG = "";
    private TextView sent_to_email;
    private TextView email_sent_message;

    private FirebaseAuth mAuth;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_password_update);
        sent_to_email = findViewById(R.id.email_text);
        email_sent_message = findViewById(R.id.email_sent_message);
        mAuth = FirebaseAuth.getInstance();
        sent_to_email.setText(getIntent().getStringExtra("email"));

    }

    /**
     *
     * @param view
     */
    public void toLogin(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }
}