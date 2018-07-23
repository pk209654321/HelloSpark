package dao.impl;

import bean.Info;
import dao.IInfoDao;
import jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/6/27.
 */
public class InfoDaoImpl implements IInfoDao {
    @Override
    public List<Info> selectInfoList() {

        String sql = "select * from info";
        Object[] params = new Object[]{};
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        //final Info info=new Info();
        final List<Info> infoList=new ArrayList<Info>();
        jdbcHelper.executeQuery(sql, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while(rs.next()) {
                    Info info=new Info();
                    info.setAccountId(rs.getLong(1));
                    info.setUserName(rs.getString(2));
                    info.setPhone(rs.getString(3));
                    info.setBrand(rs.getString(4));
                    info.setModel(rs.getString(5));
                    info.setChannel(rs.getString(6));
                    info.setTimeStr(rs.getString(7));
                    info.setSource(rs.getString(9));
                    infoList.add(info);
                }
            }
        });
        return infoList;
    }

    @Override
    public int insertInfoList(String sql, Object[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        int i = instance.executeUpdate(sql, objects);
        return i;
    }

    @Override
    public int updateInfoList(String sql, String[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        int i = instance.executeUpdate(sql, objects);
        return i;
    }
}
