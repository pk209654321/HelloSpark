package dao.impl;

import dao.ITVideoplayDao;
import jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/6/28.
 */
public class TVideoplayDaoImpl implements ITVideoplayDao {
    @Override
    public int insertTVideoplayDao(String sql,String[] objects) {
        // 获取JDBCHelper的单例
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        //String sql="insert t_videoplay () values ()"
        int i = jdbcHelper.executeUpdate(sql, objects);
        return i;
    }

    @Override
    public int updateUserTotalCount(String sql, String[] objects) {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        int i = jdbcHelper.executeUpdate(sql, objects);
        return i;
    }

    @Override
    public int update(String sql, Object[] objects) {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        int i = jdbcHelper.executeUpdate(sql, objects);

        return i;
    }

    @Override
    public int selectTVideoplayDao(String sql, String[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        final ArrayList<Integer> list = new ArrayList<>();
        instance.executeQuery(sql, objects,  new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                boolean last = rs.last();
                int row = rs.getRow();
                list.add(row);
            }
        });
        return list.get(0);
    }
}
