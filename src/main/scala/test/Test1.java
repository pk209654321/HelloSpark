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
       /* JDBCHelper instance = JDBCHelper.getInstance();
        String sql="select * from user order by id";
        instance.executeQuery(sql, new Object[]{}, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    System.out.println(rs.getString(1));
                }
            }
        });*/





    }
}
