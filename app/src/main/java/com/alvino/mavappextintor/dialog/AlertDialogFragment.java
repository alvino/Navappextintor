package com.alvino.mavappextintor.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by alvino on 31/07/15.
 */
public class AlertDialogFragment extends DialogFragment {

    private String mMessagem;
    private DialogInterface.OnClickListener mClickOk = null;
    private DialogInterface.OnClickListener mClickCancela = null;
    private String mTitle;

    public AlertDialogFragment() {

    }

    @SuppressLint("ValidFragment")
    public AlertDialogFragment(String title, String messagem, DialogInterface.OnClickListener clickOk, DialogInterface.OnClickListener clickCancela) {
        mTitle = title;
        mMessagem = messagem;
        mClickOk = clickOk;
        mClickCancela = clickCancela;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (!mTitle.equals("")) {
            builder.setTitle(mTitle);
        }
        builder.setMessage(mMessagem);

        builder.setPositiveButton("Ok", mClickOk);

        builder.setNegativeButton("Cancel", mClickCancela);

        return builder.create();
    }
}
