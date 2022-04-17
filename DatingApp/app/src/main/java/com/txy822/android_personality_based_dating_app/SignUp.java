package com.txy822.android_personality_based_dating_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * The SignUp class is for registering new users to database
 */
public class SignUp extends AppCompatActivity {
    /**
     * mAuth used to keep track of authorised users
     * mStore clause based firebase database to store users detail
     * progressDialog dialog while loading the system
     */
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private EditText full_name;
    private EditText password;
    private EditText email;
    private EditText mDob;
    private int age=0;
    private ProgressDialog progressDialog;

    public SignUp(FirebaseFirestore store, FirebaseAuth auth){
        this.mStore=store.getInstance();;
        this.mAuth=auth.getInstance();
    }
    public SignUp(){

    }
    /**
     *  Creates sign up activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth= FirebaseAuth.getInstance();
        full_name= findViewById(R.id.enter_full_name);
        password=findViewById(R.id.enter_password);
        email=findViewById(R.id.enterEmail);

        progressDialog=new ProgressDialog(getApplicationContext());
        progressDialog.setTitle("Logging in ...");
        progressDialog.setMessage("Please wait!");
        mStore=FirebaseFirestore.getInstance();
    }
    /**
     * Registers new users using full name, email and password
     * @param view view
     */
    public void signup(View view) {
        //create  a user in FirebaseFirestore  by password and email
        String full_name_=full_name.getText().toString();
        String email_= email.getText().toString();
        String password_= password.getText().toString();
        if(!email_.isEmpty() && !password_.isEmpty()&&!full_name_.isEmpty()){
            createAccount(mAuth, mStore,full_name_, email_, password_);
        }
        if(full_name_.isEmpty()|| email_.isEmpty() || password_.isEmpty()){
            Toast.makeText(getApplicationContext(),"Your Full Name or Email or Password Field  is empty!",Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Cancels the registration and go back to main class
     * @param view view
     */
    public void cancel(View view) {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    /**
     *  Switches to login or sign in activity
     * @param view view
     */
    public void login(View view) {

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public FirebaseUser createAccount(FirebaseAuth mAuth_,FirebaseFirestore mStore_,String fullName_, String email, String password){

        mAuth_.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Hash map to track of user detail
                    Map<String, Object> map=new HashMap<>();
                    map.put("fullName",fullName_);
                    //set up User collection with current user UID as document Id to store user detail in the field of document
//                    storeAccount(mAuth_,mStore_,full_name_,map).
                    mStore_.collection("Users").document(mAuth_.getCurrentUser().getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                //once creation of document is successful it switches to login activity
                                Toast.makeText(getBaseContext(),"Account Created",Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(SignUp.this,Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(getBaseContext(),""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(),""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
      FirebaseUser user= mAuth.getCurrentUser();
        return user;
    }
    public void storeAccount(FirebaseAuth auth, FirebaseFirestore store, String fullName, HashMap map){
        store.collection("Users").document(auth.getCurrentUser().getUid()).set(map);
        }
}