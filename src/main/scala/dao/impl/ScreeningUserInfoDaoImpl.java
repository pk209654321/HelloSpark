package dao.impl;

import dao.IScreeningUserInfoDao;
import jdbc.JDBCHelper;

/**
 * Created by lenovo on 2018/8/2.
 */
public class ScreeningUserInfoDaoImpl implements IScreeningUserInfoDao {
    @Override
    public int insertScreeningUserInfo(String sql, Object[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        int i = instance.executeUpdate(sql, objects);
        return i;
    }
}
