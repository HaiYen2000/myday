package com.fox.myday.activities;


import androidx.annotation.Nullable;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fox.myday.R;
import com.fox.myday.base.BaseActivity;
import com.fox.myday.base.NoStatusBarActivity;

import static com.fox.myday.Constants.SPLASH_TIME_OUT;

public class SplashActivity extends NoStatusBarActivity {

    private MediaPlayer startup_sound;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_splash);
        startup_sound = MediaPlayer.create(SplashActivity.this, R.raw.android_q_startup);
        startup_sound.start();
        imageView = findViewById(R.id.imageView);
        Glide.with(SplashActivity.this)
                .asGif()
                .load(R.raw.splash)
                .placeholder(R.drawable.android_q_logo)
                .centerCrop()
                .into(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateActivity(IntroActivity.class);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        startup_sound.release();
        finish();
    }
}
