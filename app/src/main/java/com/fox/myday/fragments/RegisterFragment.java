package com.fox.myday.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fox.myday.R;
import com.fox.myday.interfaces.RegisterView;
import com.fox.myday.listeners.OnRegisterListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import static com.fox.myday.Constants.EMAIL_REGEX;

public class RegisterFragment extends Fragment implements OnRegisterListener, View.OnClickListener, RegisterView {

    private FirebaseAuth mAuth;
    private EditText edtEmail, edtPassword, edtRePassword;
    private Button btnRegister;
    private ProgressBar progressBar;

    public RegisterFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        user.reload();
        initViews(inflate);
        btnRegister.setOnClickListener(this);
        return inflate;
    }

    private void initViews(View view){
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtRePassword = view.findViewById(R.id.edtRePassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onRegister() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String re_password = edtRePassword.getText().toString().trim();
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
        }else if(!password.equals(re_password)){
            onSamePassword();
            return;
        }else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            onRegisterSuccess();
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            onVerificationEmailSuccess();
                                        }else{
                                            onVerificationEmailFail();
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("Tag", "createUserWithEmail:failure", task.getException());
                            onRegisterFail();
                        }
                        // ...
                    });
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnRegister){
            onRegister();
        }
    }

    @Override
    public void onRegisterFail() {
        Toast.makeText(getContext(), "Registration failed !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(getContext(), "Registration succeeded !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationEmailSuccess() {
        Toast.makeText(getContext(), "The Verification  email has been sent to " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationEmailFail() {
        Toast.makeText(getContext(), "Failed to send verification email !", Toast.LENGTH_SHORT).show();
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
    public void onSamePassword() {
        Toast.makeText(getContext(), "Please input the same password !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBadFormatEmail() {
        Toast.makeText(getContext(), "Check your email format again !", Toast.LENGTH_SHORT).show();
    }
}
