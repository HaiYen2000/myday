package com.fox.myday.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fox.myday.R;
import com.fox.myday.utils.UI;

public class CommonActivity extends AppCompatActivity {

    protected int theme;
    protected boolean darkTheme;
    protected boolean blackTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(theme = UI.getTheme(prefs.getString("theme", "fresh")));
        darkTheme = theme == R.style.AppTheme_NoActionBar_Dark ||
                theme == R.style.AppTheme_NoActionBar_Classic_Dark;
        blackTheme = theme == R.style.AppTheme_NoActionBar_Black ||
                theme == R.style.AppTheme_NoActionBar_Classic_Black;

        UI.setNavigationBarMode(CommonActivity.this, darkTheme, blackTheme);
    }

}
