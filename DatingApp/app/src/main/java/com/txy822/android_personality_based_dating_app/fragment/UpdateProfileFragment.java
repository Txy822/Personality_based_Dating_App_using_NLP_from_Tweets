package com.txy822.android_personality_based_dating_app.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.DatePickerDialog;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.txy822.android_personality_based_dating_app.R;
import com.txy822.android_personality_based_dating_app.utils.TypeCompatibility;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * UpdateProfileFragment Class updates the user profile
 */
public class UpdateProfileFragment extends Fragment {
    private static final int RESULT_LOAD_IMG = 123;
    private static final int RESULT_LOAD_PLACE = 122;

    private ImageView profile_img;
    private EditText full_name;
    private EditText personality_type;
    private EditText location;
    private EditText date_of_birth;
    private EditText age_range_pref;
    private EditText summary;
    private int  age;
    final Calendar myCalendar= Calendar.getInstance();

    private Button save_profile;
    private  Button cancel;
    private Button signIn;

    private Uri url=null;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private StorageReference mStorage;
    private DatabaseReference dbRef;

    private double currentUserLatitude;
    private double currentUserLongitude;
    private  String   currentUserPlace;

    /**
     * Creates View for  fragment of update profile
     * @param inflater  LayoutInflater inflater
     * @param container ViewGroup container
     * @param savedInstanceState Bundle saved instances state
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_update_profile, container, false);
        profile_img=(ImageView)view.findViewById(R.id.profile);
        full_name=view.findViewById(R.id.full_name_id);
        personality_type=(EditText)view.findViewById(R.id.enter_personality_type);
        location=(EditText)view.findViewById(R.id.enter_location);

        //google places api access key
        Places.initialize(getActivity().getApplicationContext(), "AIzaSyDZJLLLAz2-H3C57IYT9fdAIWrrvkHaiOU");

        date_of_birth=(EditText)view.findViewById(R.id.enter_date_of_birth);
//        age=(EditText)view.findViewById(R.id.age);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            //calendar calculator
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="MM/dd/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.UK);
                date_of_birth.setText(dateFormat.format(myCalendar.getTime()));
                Calendar today= Calendar.getInstance();
                age=(today.get(Calendar.YEAR)-(myCalendar.get(Calendar.YEAR)));

            }
        };
        //listener for date of birth
        date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        age_range_pref=(EditText)view.findViewById(R.id.enter_age_range);
        summary=(EditText)view.findViewById(R.id.enter_summary);

        mAuth=FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
//        mStorage=FirebaseStorage.getInstance();
        mStorage=FirebaseStorage.getInstance().getReference();
        //displays the existing data
        if(mAuth.getCurrentUser()!=null) {
            getProfileData();
        }
//upload image for profile
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for uploading profile images
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //initialize place  field list
                    List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);
                    //create intent for place selector
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getActivity().getApplicationContext());
                    startActivityForResult(intent, RESULT_LOAD_PLACE);
                }

            });
        save_profile=view.findViewById(R.id.save);
        if(mAuth.getCurrentUser()!=null) {
            save_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //random number  for name to save images
                    Long tsLong = System.currentTimeMillis() / 1000;
                    String ts = tsLong.toString();
                    //check if the image url is not added
                    if (url != null) {
                        mStorage.child(ts + "/").putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> res = taskSnapshot.getStorage().getDownloadUrl();
                                res.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //download image uri
                                        String downloadUrl = uri.toString();
                                        //create hash map to store user profile
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("fullName", full_name.getText().toString());
                                        map.put("personalityType", personality_type.getText().toString());
                                        map.put("location", location.getText().toString());
                                        map.put("dateOfBirth", date_of_birth.getText().toString());
                                        map.put("ageRangePreference", age_range_pref.getText().toString());
                                        map.put("summary", summary.getText().toString());
                                        map.put("img_url", downloadUrl);
                                        map.put("age", age);
                                        map.put("latitude", currentUserLatitude);
                                        map.put("longitude", currentUserLongitude);

                                        //update the existing user data or profile under users collection with document id of each user UID
                                        mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getContext(), "Error Not Updated", Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });
                                    }

                                });
                            }
                        });

                    } else {

                        //if image is not uploaded earlier or if image url id empty create new one
                        Map<String, Object> map = new HashMap<>();
                        map.put("fullName", full_name.getText().toString());
                        map.put("personalityType", personality_type.getText().toString());
                        map.put("location", location.getText().toString());
                        map.put("dateOfBirth", date_of_birth.getText().toString());
                        map.put("ageRangePreference", age_range_pref.getText().toString());
                        map.put("summary", summary.getText().toString());
                        map.put("age", age);
                        map.put("latitude", currentUserLatitude);
                        map.put("longitude", currentUserLongitude);

                        //set user collection with user data using document id of current user UID
                        mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Error Not Updated", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }

                    //switch fragment after update
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.updateProfileFragmentLayout, new ViewProfileFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


                }
            });
        }
        cancel=view.findViewById(R.id.cancel_btn_profile);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), Home.class);
//                startActivity(intent);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.updateProfileFragmentLayout, new ViewProfileFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
        return  view;
    }

    /**
     * Loads profile data to fragment view
     */
    private void getProfileData() {
        mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String full_name_set=task.getResult().getString(("fullName"));
                    String personality_type_set=task.getResult().getString("personalityType");
                    String date_of_birth_set=task.getResult().getString("dateOfBirth");

                    String location_set=task.getResult().getString("location");

                    String summary_set =task.getResult().getString("summary");

                    String age_preference_set=task.getResult().getString("ageRangePreference");

                    String img_url_set=task.getResult().getString("img_url");

                    if(full_name_set!=null){
                        full_name.setText(full_name_set);
                    }
                    if(personality_type_set !=null){
                        personality_type.setText(personality_type_set);
                    }
                   if(age_preference_set !=null){
                       location.setText(location_set);
                   }
                   if(age_preference_set !=null){
                       age_range_pref.setText(age_preference_set);
                   }
                   if(date_of_birth_set !=null){
                       date_of_birth.setText(date_of_birth_set);
                   }
                   if(summary_set !=null){
                       summary.setText(summary_set);
                   }
                   if(img_url_set !=null){
                       Glide.with(getContext()).load(img_url_set).into(profile_img);
                   }

                }else {
                    Log.i("TAG","onComplete Error: profile data not fetched");
                }

            }

        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
       try {
           //switch to identify the place selector and image updater onActivityResult based on request code
           switch (requestCode) {
               //for place selector or update
               case RESULT_LOAD_PLACE:
                   if (resultCode == RESULT_OK) {
                       //create place with autocomplete from the data
                       Place place =Autocomplete.getPlaceFromIntent(data);
//                     currentUserLocation.setText(place.getAddress());
                       //current user place like London,UK
                       currentUserPlace= place.getAddress();
                       //extract latitude and longitude value of places
                       String sSource=String.valueOf(place.getLatLng());
                       sSource=sSource.replaceAll("lat/lng:","");
                       sSource=sSource.replace("(","");
                       sSource=sSource.replace(")","");
                       String [] split =sSource.split(",");
                       //extract latitude vale
                       currentUserLatitude=Double.parseDouble(split[0]);
                       //extract longitude value
                       currentUserLongitude=Double.parseDouble(split[1]);
                       //set location view by current user place
                       location.setText(currentUserPlace);

                   }
                   break;
                   //case of uploading profile
               case RESULT_LOAD_IMG:
                   if (resultCode == RESULT_OK) {
                       //get daata
                       final Uri imageUri = data.getData();
                       // assign image url value
                       url = imageUri;
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                       //display image
                       Glide.with(getContext()).load(imageUri).into(profile_img);

                   } else {
                       Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
                   }
                   break;
           }
       }catch (Exception e){
           Log.i("Tag", e.getMessage());
       }
    }
}