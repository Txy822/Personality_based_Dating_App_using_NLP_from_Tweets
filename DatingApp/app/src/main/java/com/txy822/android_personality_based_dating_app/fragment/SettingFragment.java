package com.txy822.android_personality_based_dating_app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.txy822.android_personality_based_dating_app.Home;
import com.txy822.android_personality_based_dating_app.Login;
import com.txy822.android_personality_based_dating_app.R;
import com.txy822.android_personality_based_dating_app.UpdateLoginDetail;

import java.util.Calendar;
/**
 * SettingFragment class for user preferences and logouts
 */
public class SettingFragment extends Fragment {

    private final String TAG="";
    private Uri url=null;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    //    private FirebaseStorage mStorage;
    private StorageReference mStorage;
    private DatabaseReference dbRef;

    private Button mUpdate;
    private Button logout;
    private Button deleteAccount;
    private Button updateLogin;

    /**
     * Creates setting view
     * @param inflater LayoutInflater inflater
     * @param container ViewGroup container
     * @param savedInstanceState Bundle savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_setting, container, false);

        mAuth=FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
//        mStorage=FirebaseStorage.getInstance();
        mStorage= FirebaseStorage.getInstance().getReference();
        deleteAccount=view.findViewById(R.id.delete_account_btn);

//logout
        logout=view.findViewById(R.id.logout);
        if(mAuth.getCurrentUser()!=null) {
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });
        }
////delete account
        if(mAuth.getCurrentUser()!=null) {
            deleteAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUser();
                    logout();
//                startActivity(new Intent(getContext(), Main.class));
                }
            });
        }
//        update user login
        updateLogin=view.findViewById(R.id.update_login_btn);
        updateLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateLoginDetail.class);
                startActivity(intent);
            }
        });

        mUpdate=view.findViewById(R.id.update);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Home.class);
//                Intent intent = new Intent(getContext(), AppNotification.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void deleteUser() {
        // [START delete_user]
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
        // [END delete_user]
    }
    public void logout(){
        if(mAuth.getCurrentUser() !=null) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), Login.class));
        }
    }
}