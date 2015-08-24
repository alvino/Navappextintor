package com.alvino.mavappextintor.core;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alvino on 22/08/15.
 */
public class SimplesDataFormatada {

    private static final String PATTERN = "dd/M/yyyy";


    public static String formatar(Date data){
        SimpleDateFormat sd = new SimpleDateFormat(PATTERN);
        return sd.format(data);
    }

    public static Date formatar(String data){
        SimpleDateFormat sd = new SimpleDateFormat(PATTERN);
        try {
            return sd.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("SimplesDataFormatada", "String dada=" + data);
            return null;
        }
    }
}
