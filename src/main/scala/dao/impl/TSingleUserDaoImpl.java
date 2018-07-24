package dao.impl;

import bean.SingleUser;
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
    public List<SingleUser> selectSingleUser(String sql, String[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        final List<SingleUser> list=new ArrayList<SingleUser>();
        instance.executeQuery(sql, objects, new JDBCHelper.QueryCallback() {

            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    SingleUser singleUser = new SingleUser();
                    singleUser.setId(rs.getLong(1));
                    singleUser.setSingleUserID(rs.getLong(2));
                    singleUser.setSingleUserName(rs.getString(3));
                    singleUser.setSingleUserPhone(rs.getString(4));
                    singleUser.setSingleDesire(rs.getString(5));
                    singleUser.setSingleRegion(rs.getString(6));
                    singleUser.setSingleEmployTime(rs.getLong(7));
                    singleUser.setSingleClick(rs.getLong(8));
                    singleUser.setSingleRefluxDay(rs.getLong(9));
                    singleUser.setSingleRecordDate(rs.getString(10));
                    singleUser.setSingleBuy(rs.getString(11));
                    singleUser.setQuantumBuy(rs.getLong(12));
                    singleUser.setAverageDay(rs.getLong(13));
                    singleUser.setInsertTime(rs.getString(14));
                    singleUser.setLastTime(rs.getString(15));
                    singleUser.setCourseId(rs.getLong(16));
                    singleUser.setPageType(rs.getLong(17));
                    singleUser.setAdId(rs.getLong(18));
                    list.add(singleUser);
                }
            }
        });
        return list;
    }
}
