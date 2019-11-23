package com.fox.myday.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.fox.myday.R;

public class AboutDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getText(R.string.app_name))
                .setMessage(TextUtils.concat(getText(R.string.app_version), "\n\n",
                        getText(R.string.about_description)))
                .setPositiveButton(R.string.dialog_ok, null)
                .create();
        alertDialog.show();
        ((TextView)alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        return alertDialog;
    }

}
