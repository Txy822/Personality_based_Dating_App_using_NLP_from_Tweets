package com.txy822.android_personality_based_dating_app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.txy822.android_personality_based_dating_app.Home;
import com.txy822.android_personality_based_dating_app.R;

//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

/**
 *  ViewProfileFragment To view full user profile
 *
 */
public class ViewProfileFragment extends Fragment {
    /**
     * Result Load code for uploading image
     */
    private static final int RESULT_LOAD_IMG = 123;
    /**
     * Image view  for profile picture view
     */
    private ImageView profile_img;
    /**
     * User details to view on profile
     */
    private TextView full_name;
    private TextView  personality_type;
    private TextView  location;
    private TextView  date_of_birth;
    private TextView age_range_pref;
    private TextView  summary;

    /**
     * Calander to add dates on user date of birth
     */
    final Calendar myCalendar= Calendar.getInstance();
    /**
     * Edit profile button to edit the detail of user
     */
    private Button edit_profile_btn;
    private  Button cancel;

    /**
     * Declare the firebase cloud database and authentication instances
     */
    private Uri url=null;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private StorageReference mStorage;

    /**
     * Creates view of view profile fragment
     * @param inflater LayoutInflater inflater
     * @param container ViewGroup container
     * @param savedInstanceState  Bundle
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_view_profile, container, false);
        profile_img=(ImageView)view.findViewById(R.id.profile);
        full_name=view.findViewById(R.id.fulName_id);
        personality_type=(TextView)view.findViewById(R.id.personality_type);
        location=(TextView)view.findViewById(R.id.location);
        date_of_birth=(TextView)view.findViewById(R.id.date_of_birth);

        age_range_pref=(TextView)view.findViewById(R.id.age_range);
        summary=(TextView)view.findViewById(R.id.summary);

        mAuth=FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
        mStorage=FirebaseStorage.getInstance().getReference();

        getProfileData();
        edit_profile_btn=view.findViewById(R.id.edit);
        // switch to update profile fragment to edit and update profile
        edit_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new UpdateProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.viewProfileFragmentLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        cancel=view.findViewById(R.id.cancel_btn_view_profile);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Home.class);
                startActivity(intent);

            }
        });

        return  view;
    }

    /**
     * Fetches profile from database and display on the fragment view
     */
    private void getProfileData() {
        //get the user detail fro the Users collection


        if(mAuth.getCurrentUser()!=null) {
            mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        String full_name_set = task.getResult().getString(("fullName"));
                        String personality_type_set = task.getResult().getString("personalityType");
                        String date_of_birth_set = task.getResult().getString("dateOfBirth");

                        String location_set = task.getResult().getString("location");

                        String summary_set = task.getResult().getString("summary");

                        String age_preference_set = task.getResult().getString("ageRangePreference");

                        String img_url_set = task.getResult().getString("img_url");
                        if (full_name_set != null) {
                            full_name.setText(full_name_set);
                        }
                        if (personality_type_set != null) {
                            personality_type.setText(personality_type_set);
                        }
                        if (age_preference_set != null) {
                            location.setText(location_set);
                        }
                        if (age_preference_set != null) {
                            age_range_pref.setText(age_preference_set);
                        }
                        if (date_of_birth_set != null) {
                            date_of_birth.setText(date_of_birth_set);
                        }
                        if (summary_set != null) {
                            summary.setText(summary_set);
                        }
                        if (img_url_set != null) {
                            Glide.with(getContext()).load(img_url_set).into(profile_img);
                        }

                    } else {
                        Log.i("TAG", "onComplete Error: profile data not fetched");
                    }

                }

            });
        }
        else {
            Log.i("TAG", "User don't sign in");

        }


    }

}