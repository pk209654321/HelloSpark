package test;

import bean.Role;
import mapper.RoleMapper;
import org.apache.ibatis.session.SqlSession;
import util.mybatis.SqlSessionFactoryUtil;

/**
 * Created by lenovo on 2018/8/1.
 */
public class MybatisTest {
    public static void main(String[] args) {
        SqlSession sqlSession=null;
        sqlSession = SqlSessionFactoryUtil.openSqlSession();
        RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);
		/*Role role=new Role("testName","testNote");
		roleMapper.insertRole(role);*/
        //roleMapper.deleteRole((long) 1);
        Role role=roleMapper.getRole((long)2);
        System.out.println(role.getId()+" "+role.getRoleName()+" "+role.getNote());
        sqlSession.commit();
    }
}
