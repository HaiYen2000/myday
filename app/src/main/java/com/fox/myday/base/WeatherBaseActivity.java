package com.fox.myday.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.fox.myday.R;
import com.fox.myday.activities.AboutActivity;
import com.fox.myday.activities.CalendarActivity;
import com.fox.myday.activities.NoteActivity;
import com.fox.myday.activities.SettingActivity;
import com.fox.myday.activities.ShareActivity;
import com.fox.myday.activities.WeatherActivity;
import com.fox.myday.utils.UI;
import com.google.android.material.navigation.NavigationView;

public class WeatherBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    protected int theme;
    protected boolean darkTheme;
    protected boolean blackTheme;

    //Init Navigation Drawer
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(theme = UI.getTheme(prefs.getString("theme", "fresh")));
        darkTheme = theme == R.style.AppTheme_NoActionBar_Dark ||
                theme == R.style.AppTheme_NoActionBar_Classic_Dark;
        blackTheme = theme == R.style.AppTheme_NoActionBar_Black ||
                theme == R.style.AppTheme_NoActionBar_Classic_Black;

        UI.setNavigationBarMode(WeatherBaseActivity.this, darkTheme, blackTheme);
    }

    @SuppressWarnings("StatemenWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if(item.isChecked()){
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        switch (item.getItemId()){
            case R.id.nav_calendar:
                intent = new Intent(getApplicationContext(), CalendarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startAnimatedActivity(intent);
                break;
            case R.id.nav_weather:
                intent = new Intent(getApplicationContext(), WeatherActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startAnimatedActivity(intent);
                break;
            case R.id.nav_note:
                intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startAnimatedActivity(intent);
                break;
            case R.id.nav_setting:
                intent = new Intent(getApplicationContext(), SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startAnimatedActivity(intent);
                break;
            case R.id.nav_share:
                intent = new Intent(getApplicationContext(), ShareActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startAnimatedActivity(intent);
                finish();
                break;
            case R.id.nav_about:
                intent = new Intent(getApplicationContext(), AboutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startAnimatedActivity(intent);
                break;
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void startAnimatedActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
