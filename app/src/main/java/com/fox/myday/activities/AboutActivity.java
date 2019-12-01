package com.fox.myday.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.fox.myday.R;
import com.fox.myday.base.BaseActivity;
import com.fox.myday.base.MyBounceInterpolator;
import com.fox.myday.databinding.ActivityAboutBinding;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        ActivityAboutBinding activityAboutBinding = DataBindingUtil.inflate(inflater, R.layout.activity_about, null, false);
        View contentView = activityAboutBinding.getRoot();
        drawerLayout.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_about);
        setTitle(R.string.menu_about);
        TextView tvFeedback = findViewById(R.id.tvFeedBack);
        tvFeedback.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
            builder.setTitle("Thanks for your feedback!")
                    .setMessage("Do you have anything you wish to say to the developer our app ?")
                    .setPositiveButton("Send Email", (dialog, which) -> {
                        // Your code
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setType("text/plain");
                        intent.setData(Uri.parse("mailto:"));
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"namntph06792@fpt.edu.vn"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, new String[]{getResources().getString(R.string.app_name) + " - " + Build.BRAND + " " + Build.MODEL});
                        //intent.putExtra(Intent.EXTRA_TEXT, message);
                        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        // do nothing
                    });
            AlertDialog alert =builder.create();
            alert.show();
        });
        onAnimateLogo(tvFeedback);
    }

    private void onAnimateLogo(final View view) {
        // Load the animation
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        //val animationDuration = 0.2 * 1000
        //myAnim.duration = animationDuration.toLong()

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2,20.0);

        myAnim.setInterpolator(interpolator);

        // Animate the button
        view.startAnimation(myAnim);

        // Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onAnimateLogo(view);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                super.onBackPressed();
            }
        }
    }

}
