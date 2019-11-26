package com.fox.myday.presenters;

import android.os.Handler;
import android.text.TextUtils;


import com.fox.myday.interfaces.RecoverView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import static com.fox.myday.Constants.EMAIL_REGEX;
import static com.fox.myday.Constants.FRAGMENT_TIME_OUT;

public class RecoverPresenter {

    RecoverView recoverView;
    FirebaseAuth mAuth;

    public RecoverPresenter(RecoverView recoverView) {
        this.recoverView = recoverView;
    }

    public void onRecoverPassword(String email){
        mAuth = FirebaseAuth.getInstance();
        if(TextUtils.isEmpty(email)) {
            recoverView.onEmptyEmail();
            return;
        }else if(!Pattern.compile(EMAIL_REGEX).matcher(email).matches()){
            recoverView.onBadFormatEmail();
            return;
        }else{
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            recoverView.onRecoverSuccess();
                            new Handler().postDelayed(() -> {
                                recoverView.onNavigate();
                            },FRAGMENT_TIME_OUT);
                        }else{
                            recoverView.onRecoverFail();
                        }
                    });
        }
    }
}
