package test;

import conf.ConfigurationManager;
import constant.Constants;
import dao.ITVideoplayDao;
import dao.factory.DAOFactory;
import jdbc.JDBCHelper;
import org.apache.log4j.PropertyConfigurator;

import java.sql.ResultSet;

/**
 * Created by lenovo on 2018/6/29.
 */
public class Test1 {
    public static void main(String[] args) {
        String content="SN=IOSCJPH10_GA&VN=102042517&BN=0&VC=APPLE&MO=iPhone&RL=414_736&CHID=1000&LCID=0&RV=&OS=11.1.2&DV=V1";
        String[] split = content.split("&");
        if (split.length==11){
            System.out.println("长度:" + split.length);
            //String s = split[6];
            //System.out.println(s);
        }
    }
}
