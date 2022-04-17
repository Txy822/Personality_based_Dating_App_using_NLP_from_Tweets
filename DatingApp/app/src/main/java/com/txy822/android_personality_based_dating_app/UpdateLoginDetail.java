package com.txy822.android_personality_based_dating_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateLoginDetail extends AppCompatActivity {
    private final String TAG="";
    private EditText mEmail;
    private String email;
    private EditText mPassword;
    private String password;
    private TextView text;
    private Button apply;
    FirebaseAuth mAuth;
    FirebaseUser user;

    /**
     * Default constructor for AppCompatActivity. All Activities must have a default constructor
     * for API 27 and lower devices or when using the default
     * {@link AppComponentFactory}.
     */
    public UpdateLoginDetail(String  mEmail, String  mPassword, FirebaseAuth mAuth) {
        this.email = mEmail;
        this.password = mPassword;
        this.mAuth = mAuth;
    }
    public UpdateLoginDetail(){

    }
    /**
     *  Update the user login detail
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_login_detail);
        mAuth= FirebaseAuth.getInstance();
        user=  mAuth.getInstance().getCurrentUser();
        mPassword=findViewById(R.id.enterPassword);
        mEmail=findViewById(R.id.enterEmail);
        text=findViewById(R.id.welcome_text);
        apply=findViewById(R.id.apply_btn);

        //button to update user password and email from firebase
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                update user detal using email and password
               boolean b= updateUserDetail();
//                start home intent after update
                if(b==true) {
                    Intent intent = new Intent(getApplication(), Home.class);
                    startActivity(intent);
                }
            }
        });
    }
    /**
     * Update the user detail of password and email
     */
    public boolean  updateUserDetail(){

        email= mEmail.getText().toString();
        password= mPassword.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()){
            updateEmailPassword(password,email,mAuth);
            return true;
        }
        else{
            text.setText("Please add your email and password!");
            Toast.makeText(UpdateLoginDetail.this, "Please add your email and password!", Toast.LENGTH_SHORT).show();
        }
        return  false;
    }
    /**
     * Updates email and password
     * @param userPassword
     * @param userEmail
     * @param auth
     * @return
     */
    public boolean updateEmailPassword(String userPassword, String userEmail, FirebaseAuth auth) {


        if(!userPassword.isEmpty()&& !userEmail.isEmpty()) {
            // get current user
            FirebaseUser currentUser = auth.getInstance().getCurrentUser();
            try {
              // updates the current user password
                currentUser.updatePassword(userPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateLoginDetail.this, "Password updated", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "User password updated.");
                                }
                            }
                        });
                //updates the current user email
                currentUser.updateEmail(userEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateLoginDetail.this, "email updated", Toast.LENGTH_SHORT).show();

                                    Log.d(TAG, "User email address updated.");
                                }
                            }
                        });
                //
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(UpdateLoginDetail.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                return false;

            }

        }
        else {
            Toast.makeText(UpdateLoginDetail.this, "Either email or Password field Empty", Toast.LENGTH_SHORT).show();

        }
        return true;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(EditText mEmail) {
        this.email = mEmail.getText().toString();
    }

    public void setPassword(EditText mPassword) {
        this.password = mPassword.getText().toString();
    }


}