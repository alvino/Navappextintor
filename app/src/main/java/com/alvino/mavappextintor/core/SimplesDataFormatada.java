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
    public static final String YYYYMDD = "yyyy-M-dd";
    public static final String DDMYYYY = "dd/M/yyyy";


    public static String formatar(Date data,String pattern){
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        return sd.format(data);
    }

    public static Date formatar(String data,String pattern){
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        try {
            return sd.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
