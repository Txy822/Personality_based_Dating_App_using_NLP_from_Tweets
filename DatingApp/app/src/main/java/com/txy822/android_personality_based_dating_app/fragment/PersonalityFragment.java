package com.txy822.android_personality_based_dating_app.fragment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.txy822.android_personality_based_dating_app.ChatActivity;
import com.txy822.android_personality_based_dating_app.R;

import java.util.List;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import android.os.Handler;
import android.widget.Toast;

import com.txy822.android_personality_based_dating_app.SignUp;
//import com.txy822.android_personality_based_dating_app.model.BertClassifier;
import com.txy822.android_personality_based_dating_app.model.BertClassifier;
import com.txy822.android_personality_based_dating_app.model.Result;

import java.util.ArrayList;

/**
 * PersonalityFragment Fetch tweets,load model check personality
 */
public class PersonalityFragment extends Fragment {


    private static final String TAG = "TAG";
//  twitter API authentication keys
    private  String CONSUMER_KEY="your CONSUMER_KEY";
    private  String CONSUMER_SECRET="your CONSUMER_SECRET";
    private  String ACCESS_KEY="your ACCESS_KEY";
    private  String ACCESS_SECRET="your ACCESS_SECRET";

    private Button btn_check_personality;
    private Button btn_view_some_tweets;

    ResponseList<Status> resList = null;
    private  TextView title;

    private TextView classification_result;
    private TextView result_label;
    private EditText user_name_or_screen_name;
    private int numberOfTweets=100; //  number of tweets
    private String screenName="";


    private BertClassifier client;

    private TextView fetch_some_tweets;
    private Handler handler;

    /**
     * Creates PersonalityFragment view
     * @param inflater LayoutInflater inflater
     * @param container ViewGroup container
     * @param savedInstanceState ViewGroup container
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_personality, container, false);
        btn_check_personality=view.findViewById(R.id.check_personality);
        btn_view_some_tweets=view.findViewById(R.id.view_some_tweets);

        classification_result=view.findViewById(R.id.personality_classification_result_id);
        classification_result.setMovementMethod(new ScrollingMovementMethod());
        result_label=view.findViewById(R.id.result_label_text);
        fetch_some_tweets=view.findViewById(R.id.fetch_some_tweets_id);
        fetch_some_tweets.setMovementMethod(new ScrollingMovementMethod());
        title=view.findViewById(R.id.introOfPage);
        user_name_or_screen_name=view.findViewById(R.id.twitter_screen_name);

        //initialize the classifier
        client = new BertClassifier(this.getContext());

        handler = new Handler();
        //checks personality using processed tweets
        btn_check_personality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(resList !=null) {
                    String processed_tweets = preprocess(fetch_some_tweets.getText().toString());
                    //classify the given input
                    classify(processed_tweets);
                }else {
                    Toast.makeText(getContext(), "You can't check for this username ", Toast.LENGTH_SHORT).show();
                    String s="You can't check for this username";
                    setText(classification_result,s);
                }

            }
        });
        //view some of users recent tweets
        btn_view_some_tweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            screenName=user_name_or_screen_name.getText().toString();
                            if(!screenName.isEmpty()) {
                                resList = getUserTimeLine(screenName, numberOfTweets);
                            }
                        } catch (TwitterException e) {
                            resList=null;
                            Log.i(TAG,e.getErrorMessage());

                        }
                        if(resList !=null) {
                            String s="";
                            for (Status status : resList) {
                                s = s + status.getText();
                            }
                            setText(fetch_some_tweets,s);
                        }else{
                            String s="Username is incorrect! check your username!";
//                            Toast.makeText(getContext(), "Username is incorrect! check your username!", Toast.LENGTH_SHORT).show();
                            setText(fetch_some_tweets,s);
                            Log.i(TAG, " check your username!");


                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getContext(), "check your username!", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                        }
                    }
                });

                thread.start();

            }
        });

        //adds the link to take test if they don't have twitter
        TextView linkTextView = view.findViewById(R.id.taketest);

        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.truity.com/test/type-finder-personality-test-new"));
                startActivity(Intent.createChooser(intent, "Browse with"));

            }
        });
        return view;
    }

    /**
     * Gets user time line by screen name
     * @param screenName  String username
     * @param count count number of recent tweets
     * @return  Response list of status
     * @throws TwitterException
     */
    public ResponseList<Status> getUserTimeLine(String screenName,int count) throws TwitterException {

        TwitterFactory twitterFactory = new TwitterFactory(getConfiguration().build());
        Twitter twitter = twitterFactory.getInstance();
        twitter.getAuthorization();

        Long twitterID= twitter.getId();
        Paging paging = new Paging(1, count);

        return twitter.getUserTimeline(screenName, paging);
    }

    /**
     * Configuration builder setup authentication of twitter4j API
     * @return  ConfigurationBuilder cb
     */
    public ConfigurationBuilder getConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_KEY)
                .setOAuthAccessTokenSecret(ACCESS_SECRET);
        return cb;
    }

    /**
     * Set text value of TextView
     * @param text  TextView text
     * @param s  String input s
     */
    private void setText(final TextView text,String s){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(s);

            }
        });
    }

    /**
     * preprocess the given text
     * @param s String input text
     * @return String processed text
     */
    public String preprocess( String s){

        String preprocessed="";
            // replace the given string
            // with empty string
            // except the pattern "[^a-zA-Z0-9]"
        preprocessed=s.replaceAll("https?://\\S+\\s?", "");
        preprocessed = preprocessed.replaceAll("[^0-9A-Za-z ]", " ");

        preprocessed.toLowerCase();
        return preprocessed;
    }

    /**
     * Loads the classifier
     */
    @Override
    public void onStart (){
        super.onStart();
        Log.v(TAG, "onStart");
        handler.post(
                () -> {

                    client.load();
                });

    }

    /**
     * Called when the fragment is no longer in use.  This is called
     *
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop (){
        super.onStop();
        Log.v(TAG, "onStop");
        handler.post(
                () -> {
                    client.unload();
                });

    }
    /** Send input text to TextClassificationClient and get the classify messages. */
    private void classify(final String text) {
        handler.post(
                () -> {
                    // Run text classification with TF Lite.
                    List<Result> results = client.classify(text);

                    // Show classification result on screen
                    showResult(text, results);
                });
    }
    /** Show classification result on the screen. */
    private void showResult(final String inputText, final List<Result> results) {
        // Run on UI thread as we'll updating our app UI
        getActivity().runOnUiThread(
                () -> {
                    ArrayList<String> labels = new ArrayList<>();
                    ArrayList<String> BarLabel = new ArrayList<>();
                    ArrayList<Float> probability = new ArrayList<>();

                    for (int i = 0; i < results.size(); i++) {
                        Result result = results.get(i);
                        labels.add(result.getTitle());   // Extract labels
                        probability.add(result.getConfidence());  // Extract confidence
                    }
                    String s="";

                    for(int i=0; i<probability.size()-13; i++)
                    {
                       Float z=(probability.get(i))*100;
                        int x = (z).intValue();
                        s=s+labels.get(i)+"="+(x)+"%"+"\n";
                    }
                    result_label.setText("Top 3 Results");
                    result_label.setTextColor(Color.GREEN);
                    classification_result.setText(s);


                });
    }


}
