package com.fox.myday.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fox.myday.R;
import com.fox.myday.activities.CalendarActivity;
import com.fox.myday.interfaces.LoginView;
import com.fox.myday.listeners.OnLoginListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import static com.fox.myday.Constants.EMAIL_REGEX;

public class LoginFragment extends Fragment implements OnLoginListener, View.OnClickListener, LoginView {

    private FirebaseAuth mAuth;
    private TextView tvRecoverPassword;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private ProgressBar progressBar;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        Intent intent = new Intent(getContext(), CalendarActivity.class);
        startActivity(intent);
        inflate.findViewById(R.id.forgot_password).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            mAuth.getCurrentUser().reload();
        }
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
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgot_password:
                loadRecoverFragment();
                break;
            case R.id.btnLogin:
                onLogin();
                break;
        }
    }

    private void loadRecoverFragment() {
        RecoverFragment recoverFragment = new RecoverFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_fragment, recoverFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onLoginFail() {
        Toast.makeText(getActivity(), "Sign in  failed.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getActivity(), "Sign in succeeded.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNavigate() {
        startActivity(new Intent(getActivity(), CalendarActivity.class));
        getActivity().finish();
    }

    @Override
    public void onEmptyEmail() {
        Toast.makeText(getContext(), "Email can not be empty !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyPassword() {
        Toast.makeText(getContext(), "Password can not be empty !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLengthCheckPassword() {
        Toast.makeText(getContext(), "Password at least have 5 character !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBadFormatEmail() {
        Toast.makeText(getContext(), "Check your email format again !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotVerificationEmail() {
        Toast.makeText(getContext(), edtEmail.getText().toString().trim() + " has not yet verified !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)) {
            onEmptyEmail();
            return;
        }else if(!Pattern.compile(EMAIL_REGEX).matcher(email).matches()){
            onBadFormatEmail();
            return;
        }else if(TextUtils.isEmpty(password)){
            onEmptyPassword();
            return;
        }else if(password.length() < 5){
            onLengthCheckPassword();
            return;
        }else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(!user.isEmailVerified()){
                                onNotVerificationEmail();
                            }else{
                                onLoginSuccess();
                                onNavigate();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            onLoginFail();
                        }
                    });
        }
    }

}
