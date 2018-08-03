package test;

import bean.Role;
import mapper.RoleMapper;
import org.apache.ibatis.session.SqlSession;
import util.mybatis.SqlSessionFactoryUtil;

/**
 * Created by lenovo on 2018/8/1.
 */
public class MybatisTest implements Runnable{
    private SqlSession sqlSession=SqlSessionFactoryUtil.openSqlSession(true);

    public MybatisTest(){

    }
    @Override
    public void run() {
        SqlSession sqlSession=null;
        sqlSession = SqlSessionFactoryUtil.openSqlSession(true);
        RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);
        Role role1 = new Role();
        role1.setNote("gggg");
        role1.setRoleName("aaaa");
        roleMapper.insertRole(role1);
    }

    public static void main(String[] args) {
        for (int i=1;i<=1000;i++){
            Thread t=new Thread(new MybatisTest());
            t.start();
        }
    }
}
