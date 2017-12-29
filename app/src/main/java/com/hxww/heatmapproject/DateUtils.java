package com.hxww.heatmapproject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 作者：杨荣华
 * @date 创建时间：2017/11/30 19:27
 * @description 描述：
 */

public class DateUtils {
    public static int getDatePoor(Date startDate, Date endDate) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        long timeStart = startCalendar.getTimeInMillis();
        long timeEnd = endCalendar.getTimeInMillis();
        long minute = (timeEnd - timeStart) / (1000 * 60);
        return (int) minute;
/*        long minute = (endDate.getTime() - startDate.getTime()) / (1000 * 60);
        System.out.println("时间差：" + minute);
        return (int) minute;*/
    }

    public static int compare(String s1, String s2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date dt1 = df.parse(s1);
            Date dt2 = df.parse(s2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static int compare2(String s1, String s2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(s1);
            Date dt2 = df.parse(s2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public static String addSecond(String string, int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, count * 5);
        return date2String(calendar.getTime());
    }

    public static String date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

}
