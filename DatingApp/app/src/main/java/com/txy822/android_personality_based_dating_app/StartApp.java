package com.txy822.android_personality_based_dating_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Class at the start of the application with splash of about duration of
 *  duration, countDownTimer ,countDownInterval
 */
public class StartApp extends AppCompatActivity {

    private String TAG="TAG";

    private ImageView imageView;
    private final int duration = 2000;
    private final int countDownTimer = 4000;
    private final int countDownInterval = 1000;

    /**
     * Creates start app activity
     *
     * @param savedInstanceState the saved Instance input
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_start_app);

        imageView = findViewById(R.id.start_app_icon);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(imageView, "alpha", 1, 0.4f);
        fadeOut.setDuration(duration);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imageView, "alpha", 0.4f, 1);
        fadeIn.setDuration(duration);

        //animator to fade in and fade out for some time at the start of the app
        final AnimatorSet manimatorSet = new AnimatorSet();
        manimatorSet.play(fadeIn).after(fadeOut);
        manimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                manimatorSet.start();

            }
        });
        manimatorSet.start();
        new CountDownTimer(countDownTimer, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(StartApp.this, Main.class);

                startActivity(intent);
                finish();

            }
        }.start();

    }
}