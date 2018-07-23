package dao.impl;

import bean.AiUserActionInfo;
import dao.IUserActionInfoDao;
import jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/7/9.
 */
public class UserActionInfoDaoImpl implements IUserActionInfoDao {
    @Override
    public int insertUserActionInfo(String sql, String[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        int i = instance.executeUpdate(sql, objects);
        return i;
    }

    @Override
    public int updateUserActionInfo(String sql, String[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        int i = instance.executeUpdate(sql, objects);
        return i;
    }

    @Override
    public List<AiUserActionInfo> selectUserActionInfoList(String sql, String[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        final ArrayList<AiUserActionInfo> list = new ArrayList<>();
        instance.executeQuery(sql, objects, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
              while (rs.next()){
                  AiUserActionInfo userActionInfo = new AiUserActionInfo();
                  userActionInfo.setId(rs.getLong(1));
                  userActionInfo.setUserId(rs.getString(2));
                  userActionInfo.setPlateId(rs.getString(3));
                  userActionInfo.setPageId(rs.getString(4));
                  userActionInfo.setPageUrl(rs.getString(5));
                  userActionInfo.setAction(rs.getString(6));
                  userActionInfo.setActionContent(rs.getString(7));
                  userActionInfo.setCreateTime(rs.getString(8));
                  userActionInfo.setEnterTime(rs.getString(9));
                  userActionInfo.setLeaveTime(rs.getString(10));
                  userActionInfo.setAfFirst(rs.getString(11));
                  userActionInfo.setAfSecond(rs.getString(12));
                  userActionInfo.setOperatType(rs.getString(13));
                  list.add(userActionInfo);
              }
            }
        });
        return list;
    }
}
