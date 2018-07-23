package dao.impl;

import dao.ITSingleUserDao;
import jdbc.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/7/3.
 */
public class TSingleUserDaoImpl implements ITSingleUserDao {
    @Override
    public int insertSingleUser(String sql, String[] objects) {
        // 获取JDBCHelper的单例
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        //String sql="insert t_videoplay () values ()"
        int i = jdbcHelper.executeUpdate(sql, objects);
        return i;
    }

    @Override
    public int updateSingleUser(String sql, String[] objects) {
        // 获取JDBCHelper的单例
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        //String sql="insert t_videoplay () values ()"
        int i = jdbcHelper.executeUpdate(sql, objects);
        return i;
    }

    @Override
    public int selectSingleUser(String sql, String[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        final List<Integer> list=new ArrayList<Integer>();
        instance.executeQuery(sql, objects, new JDBCHelper.QueryCallback() {

            @Override
            public void process(ResultSet rs) throws Exception {
                boolean last = rs.last();
                int row = rs.getRow();
                //System.out.println("row============" + row);
                list.add(row);
            }
        });
        return list.get(0);
    }
}
