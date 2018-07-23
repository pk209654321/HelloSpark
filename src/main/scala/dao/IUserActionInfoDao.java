package dao;

import bean.AiUserActionInfo;

import java.util.List;

/**
 * Created by lenovo on 2018/7/9.
 */
public interface IUserActionInfoDao {
    public int insertUserActionInfo(String sql,String[] objects);
    public int updateUserActionInfo(String sql,String[] objects);
    public List<AiUserActionInfo> selectUserActionInfoList(String sql, String[] objects);
}
