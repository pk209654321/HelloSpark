package dao;

/**
 * Created by lenovo on 2018/7/2.
 */
public interface ITSpecialistDao {
    public int insertTSpecialist(String sql,String[] object);
    public int updateTSpecialist(String sql,String[] object);
    public int selectTSpecialist(String sql,String[] object);
}
