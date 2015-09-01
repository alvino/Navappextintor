package com.alvino.mavappextintor.dialog;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by alvino on 30/07/15.
 */
@SuppressLint("ValidFragment")
public class DateDialog extends DialogFragment {

    DatePickerDialog.OnDateSetListener dateSetListener;

    public DateDialog(DatePickerDialog.OnDateSetListener onDateSetListener) {
        dateSetListener = onDateSetListener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monty = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, monty, day);
        return datePickerDialog;
    }
}
