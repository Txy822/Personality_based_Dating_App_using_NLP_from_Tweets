package com.txy822.android_personality_based_dating_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Main class to login existing user and new user to register
 */
public class Main extends AppCompatActivity {
    private FirebaseAuth mAuth;
    /**
     * Creates main activity
     * @param savedInstanceState saved Instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

    }

    /**
     * Check if user is already sign in
     */
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(Main.this, Home.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * Sign  in activity
     * @param view view
     */
    public void login(View view) {
        Intent intent = new Intent(this, Login.class);
//        Intent intent = new Intent(this, linear_model.class);

        startActivity(intent);
    }

    /**
     * Sign up activity
     * @param view view
     */
    public void SignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
//        Intent intent = new Intent(this, LocationTracker.class);

        startActivity(intent);
    }

}
