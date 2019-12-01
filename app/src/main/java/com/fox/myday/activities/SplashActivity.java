package com.fox.myday.activities;


import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fox.myday.R;
import com.fox.myday.base.NoStatusBarActivity;
import com.fox.myday.databinding.ActivitySplashBinding;

import static com.fox.myday.Constants.MAX_STREAM;
import static com.fox.myday.Constants.SPLASH_TIME_OUT;

public class SplashActivity extends NoStatusBarActivity {

    private SoundPool soundPool;
    private AudioManager audioManager;

    //Stream type
    private static final int streamType = AudioManager.STREAM_MUSIC;

    private boolean loaded;

    private int startup_sound;

    private float volume;

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        //ActivitySplashBinding activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        setContentView(R.layout.activity_splash);
        initViews();
        onLoadSoundPool();

        Glide.with(SplashActivity.this)
                .asGif()
                .load(R.raw.splash)
                .fitCenter()
                .into(imageView);
        new Handler().postDelayed(() -> {
            navigateActivity(IntroActivity.class);
            finish();
        },SPLASH_TIME_OUT);
    }

    private void initViews(){
        imageView = findViewById(R.id.imageView);

    }

    private void onLoadSoundPool(){
        //AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        //Current volume index or particular stream type
        float currentVolumeIndex = audioManager.getStreamVolume(streamType);

        //Get the maximum volume index for a particular stream type
        float maxVolumeIndex = audioManager.getStreamMaxVolume(streamType);

        //volume (0 -> 1)
        this.volume = currentVolumeIndex / maxVolumeIndex;

        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        this.setVolumeControlStream(streamType);

        // For Android SDK >= 21
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                    .build();

            this.soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(MAX_STREAM).build();
        }else { // for Android SDK < 21
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC, 0);
        }

        // When Sound Pool load complete.
        this.soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            loaded = true;
            if(loaded){
                float leftVolume = volume;
                float rightVolume = volume;
                soundPool.play(startup_sound, leftVolume, rightVolume, 1, 0, 1f);
            }
        });

        // Load sound file into SoundPool.
        this.startup_sound = this.soundPool.load(this, R.raw.android_q_startup,1);
    }

}
