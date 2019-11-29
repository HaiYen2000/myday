package com.fox.myday.interfaces;

public interface RegisterView {

    void onRegisterFail();

    void onRegisterSuccess();

    void onVerificationEmailSuccess();

    void onVerificationEmailFail();

    void onEmptyEmail();

    void onEmptyPassword();

    void onLengthCheckPassword();

    void onSamePassword();

    void onBadFormatEmail();

}
