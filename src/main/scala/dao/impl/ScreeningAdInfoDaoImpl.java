package dao.impl;

import dao.IScreeningAdInfoDao;
import jdbc.JDBCHelper;

/**
 * Created by lenovo on 2018/8/2.
 */
public class ScreeningAdInfoDaoImpl implements IScreeningAdInfoDao{
    @Override
    public int insertScreeningAdInfo(String sql, Object[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        int i = instance.executeUpdate(sql, objects);
        return i;
    }
}
