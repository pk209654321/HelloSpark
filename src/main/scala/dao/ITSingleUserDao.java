package dao;

/**
 * Created by lenovo on 2018/7/3.
 */
public interface ITSingleUserDao {
    public int insertSingleUser(String sql,String[] objects);
    public int updateSingleUser(String sql,String[] objects);
    public int selectSingleUser(String sql,String[] objects);
}
