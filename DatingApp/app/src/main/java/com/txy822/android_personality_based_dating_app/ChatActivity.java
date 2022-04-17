package com.txy822.android_personality_based_dating_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.txy822.android_personality_based_dating_app.adapter.ChatRecyclerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.txy822.android_personality_based_dating_app.utils.Chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

/**
 * ChatActivity class for messaging
 */
public class ChatActivity extends AppCompatActivity {
    private String TAG="TAG";
    private int counter=0;
    private int counter2=1000;

    private RecyclerView mChatRecyclerView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private EditText mChatText;
    private ImageView mSend;
    private String userToken2="";
    private String userToken3="";
    private String current_username="";
    private String current_user_id="";
    private String currentUserToken="";

    private String receiver_username="";
    private String receiver_user_id="";
    private String receiverUserToken="";

    String toId="";
    private ChatRecyclerAdapter mChatRecyclerAdapter;
    List<Chat> mChatList ;

    /**
     * Creates chat  activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        mChatRecyclerView = findViewById(R.id.chat_recycler);
        mChatText = findViewById(R.id.chat_msg);
        mSend = findViewById(R.id.chat_btn);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mChatList = new ArrayList<>();
        mChatRecyclerView.setHasFixedSize(true);
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toId=getIntent().getStringExtra("doc_id");
//        to display the previous chats on the view
        mChatRecyclerAdapter = new ChatRecyclerAdapter(this,mChatList);
        mChatRecyclerView.setAdapter(mChatRecyclerAdapter);
        //gets the current user or device token from firebase cloud messaging (FCM) for user- sign in device
        currentUserToken=getTokenInstance();
        //Creates collection to store conversation detail on cloud based firebaseFirestore database
        mStore.collection("Message").orderBy("time_stamp", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                    DocumentSnapshot snapshot=doc.getDocument();
                    //for each chat list
                    Chat chat = snapshot.toObject(Chat.class);
                    if((chat.getFrom().equals(mAuth.getCurrentUser().getUid()) || chat.getFrom().equals(toId))
                    && (chat.getTo().equals(mAuth.getCurrentUser().getUid()) || chat.getTo().equals(toId))){
                        if(!mChatList.contains(chat)) {
                            //add to the chatList to display on the view
                            mChatList.add(chat);
//                           this updates every time  when message sent
                            mChatRecyclerAdapter.notifyDataSetChanged();
                        }

                    }
                    else {
                        Log.d(TAG,"no chat instance ");

                    }
                }
            }
        });

//        this listener sends message to the receiver
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mChatText.getText().toString().isEmpty()) {
                    //Hash map to store the message information
                    Map<String, Object> map = new HashMap<>();
                    map.put("message", mChatText.getText().toString());
                    map.put("from", mAuth.getCurrentUser().getUid());
                    map.put("to", toId);
                    map.put("time_stamp", new Date());
                    //adds the message detail hash map to the  Message collection
                    mStore.collection("Message").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                mChatText.setText("");
                                Toast.makeText(ChatActivity.this, "message sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    String senderName=current_username;
                    String message= mChatText.getText().toString();
                    //get receiver token and send notification
                    sendPushNotification(toId, senderName,message);

                }
            }
        });
    }
    /**
     * Gets token instance from Firebase cloud messaging
     * @return Token for signed in device-user
     */
    public  String getTokenInstance(){
        final String[] token = {""};
        //gets token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        token[0] = task.getResult();
                        storeToken(token[0]);
                    }
                });
        return token[0];
    }
    /**
     * Send push notification to chat receiver
     * @param toId receiver user ID
     * @param senderName current user name
     * @param message message
     * @return  receiver token
     */
    private  void  sendPushNotification(String toId, String senderName, String message){

        //get the receiver token from the collection
        mStore.collection("Users").document(toId).collection("TokenCollection").document("TokenDocument").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            //collect the receiver detail
                            if (task.getResult() != null && task.getResult().getData() != null) {
                                receiver_user_id = mAuth.getCurrentUser().getUid();
                                receiver_username = task.getResult().getString("fullName");
                                receiverUserToken=task.getResult().getString("token");
                                //Send notification using FCM and device token
                                FcmNotificationSender notificationSender= new FcmNotificationSender(receiverUserToken,senderName,message,getApplicationContext(),ChatActivity.this);
                                 notificationSender.sendNotification();

                            }
                        }
                    }
                });
    }

    /**
     * Stores token to FirebaseFirestore database in the form of document and collection
     * @param token
     */
    private void  storeToken( String token) {
        //map to collect the token of user
        final Map<String, Object> map = new HashMap<>();
        //get the current user detail
        mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null && task.getResult().getData() != null) {
                                current_user_id = mAuth.getCurrentUser().getUid();
                                current_username = task.getResult().getString("fullName");
                                map.put("name", current_username);
                                map.put("token", token);
                                map.put("current_user_id", current_user_id);

                                //creates the token collection and document
                                DocumentReference docRef = mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("TokenCollection").document("TokenDocument");
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            //check if the collection is created and if yes update if not set or create
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("TokenCollection").document("TokenDocument").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
//                                                        Toast.makeText(getApplicationContext(), "Token added", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
//                                                                Toast.makeText(getApplicationContext(), "Token not created", Toast.LENGTH_SHORT).show();
                                                                Log.d(TAG, e.toString());
                                                            }
                                                        });
                                            } else {
                                                Log.d(TAG, "Document created");
                                                mStore.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("TokenCollection").document("TokenDocument").set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(getApplicationContext(), "Token added", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getApplicationContext(), "Token not created", Toast.LENGTH_SHORT).show();
                                                                Log.d(TAG, e.toString());
                                                            }
                                                        });
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });


                            }
                        }
                    }

                });


    }

}
