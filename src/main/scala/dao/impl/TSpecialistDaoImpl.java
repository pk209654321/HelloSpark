package dao.impl;

import dao.ITSpecialistDao;
import jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/7/2.
 */
public class TSpecialistDaoImpl implements ITSpecialistDao {
    @Override
    public int insertTSpecialist(String sql,String[] objects) {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        int i = jdbcHelper.executeUpdate(sql, objects);
        return i;
    }

    @Override
    public int updateTSpecialist(String sql,String[] objects) {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        int i = jdbcHelper.executeUpdate(sql, objects);
        return i ;
    }

    @Override
    public int selectTSpecialist(String sql, String[] object) {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        final List<Integer> list=new ArrayList<Integer>();
        jdbcHelper.executeQuery(sql, object, new JDBCHelper.QueryCallback() {
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
