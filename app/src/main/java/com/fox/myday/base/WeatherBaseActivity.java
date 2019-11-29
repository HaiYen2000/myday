package com.fox.myday.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import com.fox.myday.R;
import com.fox.myday.utils.UI;
import com.google.android.material.navigation.NavigationView;

public class WeatherBaseActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    protected int theme;
    protected boolean darkTheme;
    protected boolean blackTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
=======
>>>>>>> 08fa8e68fcfb27cc93206bdfa24f8dd425ff1308
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(theme = UI.getTheme(prefs.getString("theme", "fresh")));
        darkTheme = theme == R.style.AppTheme_NoActionBar_Dark ||
                theme == R.style.AppTheme_NoActionBar_Classic_Dark;
        blackTheme = theme == R.style.AppTheme_NoActionBar_Black ||
                theme == R.style.AppTheme_NoActionBar_Classic_Black;

        UI.setNavigationBarMode(WeatherBaseActivity.this, darkTheme, blackTheme);
    }
}
