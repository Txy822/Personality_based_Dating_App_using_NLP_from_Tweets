package com.txy822.android_personality_based_dating_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UpdatePassword extends AppCompatActivity {
    private final String TAG="";
    private TextView enter_email;
    private EditText mEmail;
    private String email;
    private FirebaseAuth mAuth;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        mAuth=FirebaseAuth.getInstance();
        mEmail=findViewById(R.id.enterEmail);
        enter_email=findViewById(R.id.email_text);
        reset=findViewById(R.id.send_reset_link_btn);
    }

    /**
     * Sends reset link to email in case of the user forgot his password
     * @return return true if successful
     */
    public boolean sendPasswordReset(String email) {
        // [START send_password_reset]
        if(!email.isEmpty()) {
            try {
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdatePassword.this, "Reset link send by email!", Toast.LENGTH_SHORT).show();
//                                Log.d(TAG, "Email sent.");
                                } else {
                                    Toast.makeText(UpdatePassword.this, "Error sending email!", Toast.LENGTH_SHORT).show();
//                                Log.d(TAG, "Email not sent");
                                }
                            }
                        });
                // [END send_password_reset]
            } catch (Exception e) {
                Toast.makeText(UpdatePassword.this, "Error", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {
            Toast.makeText(UpdatePassword.this, "Email field Empty", Toast.LENGTH_SHORT).show();

        }
        return true;
    }

    /**
     *  Reset password
     * @param view
     */
    public void resetPassword(View view) {
        email= mEmail.getText().toString();
        if(!email.isEmpty()) {
            sendPasswordReset(email);
            Intent intent = new Intent(getApplication(), PostPasswordUpdate.class);
            intent.putExtra("email", email);
            startActivity(intent);
        } else {
            Toast.makeText(UpdatePassword.this, "Email field Empty", Toast.LENGTH_SHORT).show();
        }
    }
}