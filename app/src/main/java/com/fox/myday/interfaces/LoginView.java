package com.fox.myday.interfaces;

public interface LoginView {

    void onLoginFail();

    void onLoginSuccess();

    void onNavigate();

    void onEmptyEmail();

    void onEmptyPassword();

    void onLengthCheckPassword();

    void onBadFormatEmail();

    void onNotVerificationEmail();

}
