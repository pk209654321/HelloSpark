package dao;

import bean.SingleUser;

import java.util.List;

/**
 * Created by lenovo on 2018/7/3.
 */
public interface ITSingleUserDao {
    public int insertSingleUser(String sql,String[] objects);
    public int updateSingleUser(String sql,String[] objects);
    public List<SingleUser> selectSingleUser(String sql, String[] objects);
}
