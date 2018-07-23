package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2018/7/2.
 * 时间差
 */
public class DateTimeUtils {

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    //计算时间差-秒,分,时
    public static double printDifference(String startDate, String endDate,String format){
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(format);
        Date end=null;
        Date start=null;
        try {
            start = simpleDateFormat.parse(startDate);
            end = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //milliseconds
        double different = end.getTime() - start.getTime();

      /*  System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);*/

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
       // long hoursInMilli = minutesInMilli * 60;
        //long daysInMilli = hoursInMilli * 24;

        //long elapsedDays = different / daysInMilli;
      //  different = different % daysInMilli;

        //long elapsedHours = different / hoursInMilli;
       // different = different % hoursInMilli;

        double elapsedMinutes = different / secondsInMilli;
       // different = different % minutesInMilli;

        //long elapsedSeconds = different / secondsInMilli;

       /* System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);*/
        return elapsedMinutes;
    }

    public static void main(String[] args) {
        DateTimeUtils obj = new DateTimeUtils();
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        try {
            Date date1 = simpleDateFormat.parse("10/10/2013 11:30:10");
            Date date2 = simpleDateFormat.parse("13/10/2013 20:35:55");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
