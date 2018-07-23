package dao.impl;

import bean.ActivitiesOperation;
import dao.IActivitiesOperationDao;
import jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/7/20.
 */
public class ActivitiesOperationDaoImpl implements IActivitiesOperationDao {
    @Override
    public List<ActivitiesOperation> selectActivitiesOperationList(String sql, Object[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        final ArrayList<ActivitiesOperation> activitiesOperations = new ArrayList<>();
        instance.executeQuery(sql, objects, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    ActivitiesOperation ao=new ActivitiesOperation();
                    ao.setId(rs.getInt(1));
                    ao.setUserId(rs.getInt(2));
                    ao.setMarkUser(rs.getInt(3));
                    ao.setDevice(rs.getString(4));
                    ao.setPageCode(rs.getString(5));
                    ao.setProduceCode(rs.getString(6));
                    ao.setEventId(rs.getString(7));
                    ao.setPhone(rs.getString(8));
                    ao.setTimestamp(rs.getString(9));
                    activitiesOperations.add(ao);
                }
            }
        });
        return activitiesOperations;
    }
}
