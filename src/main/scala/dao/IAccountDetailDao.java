package dao;

import bean.AccountDetail;

import java.util.List;

/**
 * Created by lenovo on 2018/7/12.
 */
public interface IAccountDetailDao {
    public List<AccountDetail> selectAccountDetaiList(String sql, String[] objects);

}
