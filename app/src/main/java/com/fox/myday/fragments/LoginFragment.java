package com.fox.myday.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fox.myday.R;
import com.fox.myday.activities.CalendarActivity;
import com.fox.myday.listeners.OnLoginListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment implements OnLoginListener, View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView tvRecoverPassword;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    public LoginFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        initViews(inflate);
        tvRecoverPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        return inflate;
    }

    private void initViews(View view){
        tvRecoverPassword = view.findViewById(R.id.forgot_password);
        btnLogin = view.findViewById(R.id.btnLogin);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
    }

    @Override
    public void onLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Email can not be empty", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getContext(), "Password can not be empty", Toast.LENGTH_SHORT).show();
            edtPassword.requestFocus();
        }
        if(password.length() < 5){
            Toast.makeText(getContext(), "Password at least have 5 character", Toast.LENGTH_SHORT).show();
            edtPassword.requestFocus();
        }
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getActivity(), "Sign in succeeded.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getActivity(), CalendarActivity.class));
                            getActivity().finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "Sign in  failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgot_password:

                break;
            case R.id.btnLogin:
                onLogin();
                break;
        }
    }
}
