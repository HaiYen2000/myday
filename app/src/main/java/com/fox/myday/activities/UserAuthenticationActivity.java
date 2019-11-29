package com.fox.myday.activities;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;

import com.fox.myday.R;
import com.fox.myday.base.NoStatusBarActivity;
import com.fox.myday.databinding.ActivityUserAuthenticationBinding;
import com.fox.myday.fragments.LoginFragment;
import com.fox.myday.fragments.RegisterFragment;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.fox.myday.Constants.ORDER_LOGIN_STATE;
import static com.fox.myday.Constants.ORDER_REGISTER_STATE;

public class UserAuthenticationActivity extends NoStatusBarActivity {

    private ActivityUserAuthenticationBinding binding;
    private boolean isLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_authentication);

        LoginFragment topLoginFragment = new LoginFragment();
        RegisterFragment topSignUpFragment = new RegisterFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.login_fragment, topLoginFragment)
                .replace(R.id.register_fragment, topSignUpFragment)
                .commit();

        binding.loginFragment.setRotation(-90);

        binding.btnAuthenticate.setOnSignUpListener(topSignUpFragment);
        binding.btnAuthenticate.setOnLoginListener(topLoginFragment);

        binding.btnAuthenticate.setOnButtonSwitched(isLogin -> {
            binding.getRoot()
                    .setBackgroundColor(ContextCompat.getColor(
                            this,
                            isLogin ? R.color.firstPage : R.color.secondPage));
        });

        binding.loginFragment.setVisibility(INVISIBLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.loginFragment.setPivotX(binding.loginFragment.getWidth() / 2);
        binding.loginFragment.setPivotY(binding.loginFragment.getHeight());
        binding.registerFragment.setPivotX(binding.registerFragment.getWidth() / 2);
        binding.registerFragment.setPivotY(binding.registerFragment.getHeight());
    }

    public void switchFragment(View v) {
        if (isLogin) {
            binding.loginFragment.setVisibility(VISIBLE);
            binding.loginFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.registerFragment.setVisibility(INVISIBLE);
                    binding.registerFragment.setRotation(90);
                    binding.containerWrapper.setDrawOrder(ORDER_LOGIN_STATE);
                }
            });
        } else {
            binding.registerFragment.setVisibility(VISIBLE);
            binding.registerFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.loginFragment.setVisibility(INVISIBLE);
                    binding.loginFragment.setRotation(-90);
                    binding.containerWrapper.setDrawOrder(ORDER_REGISTER_STATE);
                }
            });
        }

        isLogin = !isLogin;
        binding.btnAuthenticate.startAnimation();
    }

}
