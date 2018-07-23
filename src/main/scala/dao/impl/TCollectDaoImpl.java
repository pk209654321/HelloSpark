package dao.impl;

import bean.TCollect;
import dao.ITCollectDao;
import jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/6/27.
 */
public class TCollectDaoImpl implements ITCollectDao {
    @Override
    public List<TCollect> selectTCollectList(String sql,String[] objects) {
        //String sql = "select * from t_collect where TO_DAYS(NOW()) - TO_DAYS(create_time) >=1";
        Object[] params = new Object[]{};
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        final List<TCollect> list=new ArrayList<TCollect>();
        jdbcHelper.executeQuery(sql,params,new JDBCHelper.QueryCallback(){
            @Override
            public void process(ResultSet rs) throws Exception {
                while(rs.next()){
                    TCollect collect=new TCollect();
                    collect.setId(rs.getInt(1));
                    collect.setCreateTime(rs.getString(2));
                    collect.setUpdateTime(rs.getString(3));
                    collect.setUserId(rs.getInt(4));
                    collect.setDevice(rs.getInt(5));
                    collect.setDeviceId(rs.getString(6));
                    collect.setSource(rs.getInt(7));
                    collect.setVersion(rs.getString(8));
                    collect.setCategory(rs.getString(9));
                    collect.setEvent(rs.getString(10));
                    collect.setEventId(rs.getString(11));
                    collect.setEventName(rs.getString(12));
                    collect.setEnterTime(rs.getString(13));
                    collect.setLeaveTime(rs.getString(14));
                    collect.setDuration(rs.getInt(15));
                    collect.setPageUrl(rs.getString(16));
                    collect.setPageTitle(rs.getString(17));
                    collect.setCourseId(rs.getInt(18));
                    collect.setCatalogId(rs.getInt(19));
                    collect.setAdviserId(rs.getInt(20));
                    collect.setIp(rs.getString(21));
                    collect.setImei(rs.getString(22));
                    collect.setReserve(rs.getString(23));
                    //System.out.println("getSource"+collect.getEnterTime());
                    list.add(collect);
                }
            }
        });
        return list;
    }
}
