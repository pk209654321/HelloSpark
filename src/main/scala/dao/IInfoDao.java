package dao;

import bean.Info;

import java.util.List;

/**
 * Created by lenovo on 2018/6/27.
 */
public interface IInfoDao {

    public List<Info> selectInfoList();

    public int insertInfoList(String sql,Object[] objects);

    public int updateInfoList(String sql,String[] objects);
}
