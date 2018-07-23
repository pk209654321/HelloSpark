package dao;

import bean.TCollect;

import java.util.List;

/**
 * Created by lenovo on 2018/6/27.
 */
public interface ITCollectDao {
    public List<TCollect> selectTCollectList(String sql,String[] objects);
}
