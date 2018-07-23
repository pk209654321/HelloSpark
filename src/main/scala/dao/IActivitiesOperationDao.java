package dao;

import bean.ActivitiesOperation;

import java.util.List;

/**
 * Created by lenovo on 2018/7/20.
 */
public interface IActivitiesOperationDao {
    public List<ActivitiesOperation> selectActivitiesOperationList(String sql,Object[] objects);
}
