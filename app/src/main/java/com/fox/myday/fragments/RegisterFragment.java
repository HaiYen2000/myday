package com.fox.myday.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fox.myday.R;
import com.fox.myday.listeners.OnRegisterListener;

public class RegisterFragment extends Fragment implements OnRegisterListener {

    public RegisterFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_register, container, false);


        return inflate;
    }

    @Override
    public void onRegister() {

    }

}
