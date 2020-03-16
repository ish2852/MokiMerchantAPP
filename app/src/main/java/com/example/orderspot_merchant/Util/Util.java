package com.example.orderspot_merchant.Util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Util {
    public static Random random = new Random();

    public  static  boolean isSuccess(String success){
        if(success.equals("True"))
            return true;
        return false;
    }

    static public String getToDayString(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        return formatDate.format(calendar.getTime());
    }

    static public Calendar getCalendarByString(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        calendar.setTime(format.parse(date));
        return calendar;
    }

    public static int getRandomInt(int size){
        return random.nextInt(size);
    }

    public static boolean isCalendarOfPast(Calendar toDayCalendar, Calendar calendar){
        Long dateDifference = (toDayCalendar.getTimeInMillis() - calendar.getTimeInMillis()) / 1000 / (24*60*60) ;
        if(dateDifference < 0){
            return false;
        }
        return true;
    }
}
