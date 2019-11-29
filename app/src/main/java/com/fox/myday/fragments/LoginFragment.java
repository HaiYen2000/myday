package com.fox.myday.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fox.myday.R;
import com.fox.myday.activities.CalendarActivity;
import com.fox.myday.listeners.OnLoginListener;

public class LoginFragment extends Fragment implements OnLoginListener {

    public LoginFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        Intent intent = new Intent(getContext(), CalendarActivity.class);
        startActivity(intent);
        inflate.findViewById(R.id.forgot_password).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        return inflate;
    }

    @Override
    public void onLogin() {

    }

}
