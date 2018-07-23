package dao;

/**
 * Created by lenovo on 2018/6/28.
 */
public interface ITVideoplayDao {
    //insert
    public int insertTVideoplayDao(String sql, String[] objects);

    //update截至到当天总用户量
    public int updateUserTotalCount(String sql,String[] objects);

    public int update(String sql,Object[] objects);

    public int selectTVideoplayDao(String sql,String[] objects);
}
