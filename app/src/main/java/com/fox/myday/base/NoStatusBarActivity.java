package com.fox.myday.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoStatusBarActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void hideStatusBar(){
        if(Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            getActionBar().hide();
        }else{
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            getSupportActionBar().hide();
        }
    }


    public void navigateActivity(Class target){
        Intent intent = new Intent(this,target);
        startActivity(intent);
    }

}
