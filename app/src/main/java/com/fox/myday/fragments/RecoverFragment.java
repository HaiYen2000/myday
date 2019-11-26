package com.fox.myday.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fox.myday.R;
import com.fox.myday.interfaces.RecoverView;
import com.fox.myday.presenters.RecoverPresenter;

public class RecoverFragment extends Fragment implements View.OnClickListener, RecoverView {

    private EditText edtEmail;
    private Button btnRecover;
    private RecoverPresenter recoverPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_recover, container, false);
        initViews(inflate);
        btnRecover.setOnClickListener(this);
        return inflate;
    }

    private void initViews(View view) {
        edtEmail = view.findViewById(R.id.edtEmail);
        btnRecover = view.findViewById(R.id.btnRecover);
        recoverPresenter = new RecoverPresenter(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnRecover){
            recoverPresenter.onRecoverPassword(edtEmail.getText().toString().trim());
        }
    }

    @Override
    public void onRecoverFail() {
        Toast.makeText(getContext(), "We can not send the reset password email !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecoverSuccess() {
        Toast.makeText(getContext(), "Recover password email has been sent !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNavigate() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onEmptyEmail() {
        Toast.makeText(getContext(), "Email can not be empty !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBadFormatEmail() {
        Toast.makeText(getContext(), "Check your email format again !", Toast.LENGTH_SHORT).show();
    }
}
