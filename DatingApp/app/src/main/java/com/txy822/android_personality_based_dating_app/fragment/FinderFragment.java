package com.txy822.android_personality_based_dating_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.txy822.android_personality_based_dating_app.R;
import com.txy822.android_personality_based_dating_app.utils.Profile;
import com.txy822.android_personality_based_dating_app.utils.TypeCompatibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * FinderFragment allows user to find matches
 * by liking or disliking them
 */
public class FinderFragment extends Fragment {

    private ImageView imageView;
    private TextView nameAgeLocation;
    private TextView personalityTypeAgePref;
    private TextView summary;


    private TextView compatibilityView;

    private Profile mProfile;
    private Context mContext;
    private ImageButton accept;
    private ImageButton reject;
    private ImageButton chat_btn;
    private int pressedCounter=0;
    private Map<Profile, String> docIdMap;
    private int compatiblity=0;
    private Profile currentUserProfile=null;

//    for compatibility
    private TypeCompatibility typeCompatibility;

    //list of 16 MBTI personality type indicator for  compatibility match
    private String [] types ={"ESTJ","ESFJ","ENFJ","ENTJ","ESTP","ESFP","ENFP","ENTP","ISTP","ISFP","INFP","INTP","ISTJ","ISFJ","INFJ","INTJ"};


    /**
     *     sender vs receiver data
     */

    private String current_username="";
    private String receiver_name="";
    private String current_image="";
    private String receiver_image="";

    /**
     * firebase instances of authentication and database
     */
    private Uri url=null;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStoreDatabase;
    private int numberOfUser=0;
    List<Profile> mProfileList;

//    user distance
//private  double distance=0;
private double currentUserLatitude, currentUserLongitude=0;
private double matchLatitude, matchLongitude=0;

private TextView distanceView;

    /**
     * Creates finder fragment view
     * @param inflater LayoutInflater inflater
     * @param container ViewGroup container
     * @param savedInstanceState savedInstanceState
     * @return View
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finder, container, false);
        typeCompatibility = new TypeCompatibility();

//        for profile image
        imageView = view.findViewById(R.id.imageView);
        compatibilityView = view.findViewById(R.id.compatiblity);
//        to display the user information
        nameAgeLocation = view.findViewById(R.id.nameAgeLocationX);
        personalityTypeAgePref = view.findViewById(R.id.personalityTypeAgePreferenceX);
        summary = view.findViewById(R.id.summaryX);
        distanceView = view.findViewById(R.id.distanceView);

        //sets compatibility map
        typeCompatibility.setCompatibilityMap(types);

//        initialize the view ids
        accept = view.findViewById(R.id.acceptBtn);
        reject = view.findViewById(R.id.rejectBtn);
        chat_btn = view.findViewById(R.id.chat_btn_id);
        mProfileList = new ArrayList<>();
        docIdMap = new HashMap<>();

//      initialize firebase authentication and  storage variables
        mAuth = FirebaseAuth.getInstance();

        fireStoreDatabase = FirebaseFirestore.getInstance();//for user storage
//        mStorage= FirebaseStorage.getInstance().getReference();//for image storage
        mContext = view.getContext();

        if(mAuth.getCurrentUser()!=null) {
//      get users data from collection
        fireStoreDatabase.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
//                  for each document  in documents
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                      get document ID
                        String docId = documentSnapshot.getId();
//                      get user Id
                        String userUID = mAuth.getCurrentUser().getUid();
//                     //identify current user
                        if (docId.equals(userUID)) {
                            //fetch current user profile
                            currentUserProfile = documentSnapshot.toObject(Profile.class).withId(userUID);
                        }
//                        check if they are not equal and add to profile list and map
                        if (!docId.equals(userUID)) {
                            //creates user profile  from the stored collection and adds to the profile list
                            Profile profile = documentSnapshot.toObject(Profile.class).withId(docId);
                            if (profile != null) {
                                mProfileList.add(profile);
                                docIdMap.put(profile, docId);
                            } else {
                                Log.i("TAG", "profile null");
                            }
                        } else {
                            Log.i("TAG", "no  users on list");
                        }
                    }
                    //setup the finder view fragment with the user detail.
                    if (mProfileList.size() != 0) {
                        numberOfUser = mProfileList.size();
                        Log.i("Number of Users", Integer.toString(numberOfUser));
                        Profile profile = mProfileList.get(pressedCounter);
                        //calculate  distance from longitude and lattitude
                        Double distance = calculateDistance(currentUserProfile.getLatitude(), currentUserProfile.getLongitude(), profile.getLatitude(), profile.getLongitude());
                        if (distance == 0) {
                            //for unknown distance
                            distanceView.setText("? miles");
                        } else {
                            //set distance view with the value
                            distanceView.setText(String.format(Locale.UK, "%.2f miles", distance));
                        }

//                      add user and user details on finder fragment view
                        Glide.with(mContext).load(profile.getImg_url()).into(imageView);
                        nameAgeLocation.setText("Name: " + profile.getFullName() + "  Age: " + profile.getAge() + "  Location: " + profile.getLocation());
                        personalityTypeAgePref.setText("Type: " + profile.getPersonalityType() + "  Age Preference: " + profile.getAgeRangePreference());
                        summary.setText("Summary: " + profile.getSummary());
                        //add comaptibility
                        compatiblity = typeCompatibility.getCompatibility(currentUserProfile.getPersonalityType(), profile.getPersonalityType());
                        //check if comaptibility is known or not
                        if (compatiblity == 0) {
                            //for unknown value
                            compatibilityView.setText("?% Personality match");
                        } else {
                            compatibilityView.setText(compatiblity + "% Personality match");

                        }
                    } else {
                        Log.i("Tag", " number of user is 0");
                    }

                    // user be able to like other users by clicking like button.
                    accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            acceptUser();
                        }
                    });
                    // user be able to reject or dislike other users by  clicking dislike button.
                    reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rejectUser();
                        }
                    });
                    //user be able to chat with the other user if both like each other
                    chat_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                          switch fragment to chat as some more matches found
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.finderFragmentLayout, new ChatFragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //do do for checking if both are liked each other and other personality requirements
                        }
                    });
                }
            }
        });
    }
        return  view;
    }

    /**
     *  Stores liked lists to users collection and deleted once both liked each other
     * @param docId  String document ID
     */
    public void getLiked(String docId){
//      get user data from user and likes collection collection
        fireStoreDatabase.collection("Users").document(docId).collection("Likes")
                .document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        Toast.makeText(mContext, ""+docId, Toast.LENGTH_SHORT).show();
                        fireStoreDatabase.collection("Users").document(docId).collection("Likes")
                                .document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    if(task.getResult()!=null && task.getResult().getData()!=null){
                                        fireStoreDatabase.collection("Users").document(docId).collection("Likes")
                                                .document(mAuth.getCurrentUser().getUid()).delete();
                                        Toast.makeText(getContext(), "Match Found", Toast.LENGTH_SHORT).show();
                                                storeMatchInDatabase(docId);
//                                        storeMatchInDatabase(docId,name);
                                    }else {
                                        //set up likes document list on the database
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("like",true);
                                        fireStoreDatabase.collection("Users").document(mAuth.getCurrentUser().getUid())
                                                .collection("Likes")
                                                .document(docId).set(map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
//                                                            Toast.makeText(mContext, "Liked!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }else{
                                    Map<String,Object> map=new HashMap<>();
                                    map.put("like",true);
                                    fireStoreDatabase.collection("Users").document(mAuth.getCurrentUser().getUid())
                                            .collection("Likes")
                                            .document(docId).set(map)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
//                                                        Toast.makeText(mContext, "Liked!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                    }
                });
    }

    /**
     * Stores matched users to database
     * @param docId String document ID
     */
    private void  storeMatchInDatabase(String docId){
        final Map<String,Object> map = new HashMap<>();
        //check if the map has got the docID already
        if(!map.containsValue(docId)) {
            //create hash map to store matches when liked each other
            map.put("user_id", docId);

            //fetch the current user data
            fireStoreDatabase.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null && task.getResult().getData() != null) {
                                    current_username = task.getResult().getString("fullName");
                                    current_image = task.getResult().getString("img_url");
                                    fireStoreDatabase.collection("Users").document(docId).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult() != null && task.getResult().getData() != null) {
                                                            //match details
                                                            receiver_name = task.getResult().getString("fullName");
                                                            receiver_image = task.getResult().getString("img_url");
                                                            map.put("fullName", receiver_name);
                                                            map.put("img_url", receiver_image);
                                                            fireStoreDatabase.collection("Users").document(mAuth.getCurrentUser().getUid())
                                                                    .collection("Match").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                    if (task.isSuccessful()) {
                                                                        map.put("user_id", mAuth.getCurrentUser().getUid());
                                                                        map.put("fullName", current_username);
                                                                        map.put("img_url", current_image);//must br removed
                                                                        //add  map to match collection
                                                                        fireStoreDatabase.collection("Users").document(docId)
                                                                                .collection("Match").add(map)
                                                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                        if (task.isSuccessful()) {
//                                                                                        Toast.makeText(getContext(), "Match Added", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    }
                                                                                });
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    });
        }

    }

    /**
     * Accepted user method
     */
    public void acceptUser(){
        pressedCounter++;

        if(pressedCounter>=numberOfUser){
            pressedCounter=0;
        }

        Profile profile=mProfileList.get(pressedCounter);


        Double distance=calculateDistance(currentUserProfile.getLatitude(),currentUserProfile.getLongitude(), profile.getLatitude(),profile.getLongitude());

        if(distance==0){
            distanceView.setText("? miles");                        }
        else {
            distanceView.setText(String.format(Locale.UK, "%.2f miles", distance));
//                        distanceView.setText(String.valueOf(distance));
        }

        Glide.with(mContext).load(profile.getImg_url()).into(imageView);

        Glide.with(mContext).load(profile.getImg_url()).into(imageView);
        nameAgeLocation.setText("Name: "+profile.getFullName() +"  Age: "+profile.getAge() + "  Location: " + profile.getLocation());
        personalityTypeAgePref.setText("Type: "+profile.getPersonalityType() + "  Age Preference: "+ profile.getAgeRangePreference());
        summary.setText("Summary: "+profile.getSummary());

        compatiblity=typeCompatibility.getCompatibility(currentUserProfile.getPersonalityType(),profile.getPersonalityType());
        if(compatiblity==0) {
            compatibilityView.setText("?% Personality match");
        }
        else {
            compatibilityView.setText(compatiblity + "% Personality match");
        }
        getLiked(docIdMap.get(profile));

    }

    /**
     *  reject user method to capture details of rejected used
     */
    public void rejectUser(){
        pressedCounter++;
        if(pressedCounter>=numberOfUser){
            pressedCounter=0;
        }
        Profile profile=mProfileList.get(pressedCounter);


        Double distance=0.0;
        distance=calculateDistance(currentUserProfile.getLatitude(),currentUserProfile.getLongitude(), profile.getLatitude(),profile.getLongitude());

        if(distance==0){
            distanceView.setText("? miles");                        }
        else {
            distanceView.setText(String.format(Locale.UK, "%.2f miles", distance));
//                        distanceView.setText(String.valueOf(distance));
        }

        Glide.with(mContext).load(profile.getImg_url()).into(imageView);
        nameAgeLocation.setText("Name: "+profile.getFullName() +"  Age: "+profile.getAge() + "  Location: " + profile.getLocation());
        personalityTypeAgePref.setText("Type: "+profile.getPersonalityType() + "  Age Preference: "+ profile.getAgeRangePreference());
        summary.setText("Summary: "+profile.getSummary());

        compatiblity=typeCompatibility.getCompatibility(currentUserProfile.getPersonalityType(),profile.getPersonalityType());
        if(compatiblity==0) {
            compatibilityView.setText("?% Personality match");
        }
        else {
            compatibilityView.setText(compatiblity + "% Personality match");

        }
    }

    /**
     * Calculates distance and return mile value
     * @param lat1 latitude of current user
     * @param log1 longitude of current user
     * @param lat2 latitude of match
     * @param log2 longitude of match
     * @return distance in miles
     */
    public Double calculateDistance(double lat1, double log1, double lat2, double log2){
        double distance=0;
        //find the difference between longitudes
        double longitudeDifference= log1-log2;

        //calculate distance in
        // from the equation Distance, d = 3963.0 * arccos[(sin(lat1) * sin(lat2)) + cos(lat1) * cos(lat2) * cos(long2 – long1)]
        distance=Math.sin(deg2rad(lat1))
                *Math.sin(deg2rad(lat2))
                +Math.cos(deg2rad(lat1))
                *Math.cos(deg2rad(lat2))
                *Math.cos(deg2rad(longitudeDifference));
        distance=Math.acos(distance);

//        convert  radian to degree
        distance =rad2deg(distance);
//        distance in miles
        distance =  (distance*60*1.1515);
//        distance in killometers
//        distance= distance*1.609344;
        return distance;
    }
    private double rad2deg(double distance){
        return (distance*180.0/Math.PI);
    }
    private double deg2rad(double lat1){
        return(lat1*Math.PI/180.0);
    }





}