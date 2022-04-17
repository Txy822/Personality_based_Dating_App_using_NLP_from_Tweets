package com.txy822.android_personality_based_dating_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

/**
 * Login class
 */
public class Login extends AppCompatActivity {
    private final String TAG="";
    private EditText mEmail;
    private EditText mPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private Button forgotPassword;

    public Login( FirebaseAuth auth){
        this.mAuth=auth;
    }
    public Login(){

    }

    /**
     * creates login activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail=findViewById(R.id.enterEmail);
        mPassword=findViewById(R.id.enterPassword);
        forgotPassword=findViewById(R.id.forgotPassword_btn);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(getApplicationContext());
        progressDialog.setTitle("Logging in ...");
        progressDialog.setMessage("Please wait!");

    }

    /**
     * Checks is user is registered on firebaseFirestore by email and password to login
     * @param view view
     */
    public void login(View view) {
        String email= mEmail.getText().toString();
        String password= mPassword.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()){
            signIn(email, password);
        }
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Email or Password empty!",Toast.LENGTH_SHORT).show();

        }

    }

    /**
     *  Creates Sign up activity for new users
     * @param view view
     */
    public void createAccount(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    /**
     *  Switches to the main class if canceled
     * @param view view
     */
    public void cancel(View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void sendPasswordReset(View view) {
        Intent intent = new Intent(this, UpdatePassword.class);
        startActivity(intent);
    }
    public FirebaseUser signIn( String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),Home.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), ""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        });
        FirebaseUser user= mAuth.getCurrentUser();
        return user;
    }
}