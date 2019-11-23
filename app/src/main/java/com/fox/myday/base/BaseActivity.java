package com.fox.myday.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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
import com.fox.myday.activities.WeatherActivity;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
    }

    public void navigateActivity(Class target){
        Intent intent = new Intent(this,target);
        startActivity(intent);
    }

    public void showMessage(String mess){
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
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
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareSubject = getResources().getString(R.string.share_subject);
                String shareBody = getResources().getString(R.string.share_content);
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share via"));
                break;
            case R.id.nav_about:
                intent = new Intent(getApplicationContext(), AboutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startAnimatedActivity(intent);
                finish();
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
