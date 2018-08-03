package dao.factory;

import mapper.RoleMapper;
import org.apache.ibatis.session.SqlSession;
import util.mybatis.SqlSessionFactoryUtil;

/**
 * Created by lenovo on 2018/8/3.
 */
public class SqlSessionFactory {
    private static SqlSession sqlSession=SqlSessionFactoryUtil.openSqlSession(true);
    public static RoleMapper getRoleMapper(){
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        return roleMapper;
    }
}
