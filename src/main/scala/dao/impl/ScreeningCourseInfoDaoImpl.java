package dao.impl;

import dao.IScreeningCourseInfoDao;
import jdbc.JDBCHelper;

/**
 * Created by lenovo on 2018/8/2.
 */
public class ScreeningCourseInfoDaoImpl implements IScreeningCourseInfoDao{
    @Override
    public int insertScreeningCourseInfo(String sql, Object[] objects) {
        JDBCHelper instance = JDBCHelper.getInstance();
        int i = instance.executeUpdate(sql, objects);
        return i;
    }
}
