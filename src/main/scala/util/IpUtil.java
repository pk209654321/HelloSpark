package util;

import conf.ConfigurationManager;
import constant.Constants;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by lenovo on 2018/7/9.
 */
public class IpUtil {
    //获取本地ip地址
    public static String getLocalIp() {
        String ip=null;
        try {
           ip= InetAddress.getLocalHost().getHostAddress().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    //判断是否为本地测试环境
    public static boolean judgeLocal(){
        String localIp = getLocalIp();
        String property = ConfigurationManager.getProperty(Constants.LOCAL_FLAG_IP);
        System.out.println("property:"+property);
        System.out.println("localIp:"+localIp);
        if(localIp.equals(property)){
            return true;
        }
        return false;
    }
}
