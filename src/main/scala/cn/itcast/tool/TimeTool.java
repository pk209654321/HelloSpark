package cn.itcast.tool;




import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2018/6/26.
 */
public class TimeTool {
    public static String testTimestampToString(long timeStr) {

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date date = new Date();
        try {
            date = ts;
            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }



    public static void main(String[] args) {
        String s = TimeTool.timeStamp2Date("1525239202","");
        System.out.println(s);
    }
}
