package cn.itcast.tool;

import cn.itcast.bean.TAccountResigterInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/6/26.
 */
public class PhoenixConnectionTool {




    public static Connection getConnLocal() {
        String driver = "org.apache.phoenix.jdbc.PhoenixDriver";
        String url = "jdbc:phoenix:master:2181";
        String username = "dengtacj";
        String password = "dengtacj2015";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static int insertUser(){
        Connection conn = getConnLocal();
        String sql="upsert into user(id, account, passwd) values('005','admin','admin')";
        PreparedStatement pstmt;
        int i=0;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public static int insertResigterInfo(TAccountResigterInfo info) {
        Connection conn = getConnLocal();
        int i = 0;
        String sql = "insert into info (accountId,userName,phone,brand,model,channel,timeStr) values(?,?,?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setLong(1, info.getiAccountId());
            pstmt.setString(2, info.getsUserName());
            pstmt.setString(3, info.getsPhone());
            pstmt.setString(4, info.getBrand());
            pstmt.setString(5, info.getModel());
            pstmt.setString(6, info.getChannel());
            pstmt.setString(7, info.getTimeStr());
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    //查询数据库
    public static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        //jdbc:mysql://localhost:3306/test?user=root&password=&useUnicode=true&characterEncoding=gbk&autoReconnect=true&failOverReadOnly=false
        String url = "jdbc:mysql://rm-bp1u2tab1ka0ed8b5mo.mysql.rds.aliyuncs.com:3306/db_account?autoReconnect=true&characterEncoding=utf-8";
        String username = "sscf_admin";
        String password = "Sscfadmin888";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    //查
    public static List<TAccountResigterInfo> getAllResigterInfoList() {
        List<TAccountResigterInfo> list=new ArrayList<TAccountResigterInfo>();

        Connection conn = getConn();
        //String sql = "select * from students";
        //SELECT * FROM `order` WHERE TO_DAYS(NOW()) - TO_DAYS(order_time) = 1

        String sql="SELECT iAccountId," +
                "sUserName," +
                "sPhone," +
                "sDUA," +
                "time " +
                "FROM " +
                "t_account_resigter_info " +
                "WHERE TO_DAYS(NOW()) - TO_DAYS(from_unixtime(time))=3";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("============================");
            while (rs.next()) {
                TAccountResigterInfo tAccountResigterInfo=new TAccountResigterInfo();
                tAccountResigterInfo.setiAccountId(rs.getInt(1));
                tAccountResigterInfo.setsUserName(rs.getString(2));
                tAccountResigterInfo.setsPhone(rs.getString(3));
                tAccountResigterInfo.setsDUA(rs.getString(4));
                tAccountResigterInfo.setTimeStr(TimeTool.timeStamp2Date(rs.getInt(5) + "",""));
                list.add(tAccountResigterInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
       // ConnectionTool.getAll();
        for (int i=0;i<=1000000;i++){
            int insertUser = PhoenixConnectionTool.insertUser();
            System.out.println("result:"+insertUser);
        }



    }
}
