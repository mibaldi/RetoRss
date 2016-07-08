package com.mibaldi.retorss2.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class DateFormatter {
    public static String convertDateToString(Date indate)
    {
        String dateString = null;
        SimpleDateFormat sdfr =  new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
        try{
            dateString = sdfr.format( indate );
        }catch (Exception ex ){
            System.out.println(ex);
        }
        return dateString;
    }
    public static Date convertStringToDate(String pubDate){
        DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
        Date date = null;
        try {
            date = formatter.parse(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
