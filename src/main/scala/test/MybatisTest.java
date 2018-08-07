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
    private RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);
    @Override
    public void run() {
        Role role1 = new Role();
        role1.setNote("gggg");
        role1.setRoleName("aaaa");
        roleMapper.insertRole(role1);
    }

    public static void main(String[] args) {
        for (int i=1;i<=1;i++){
            Thread t=new Thread(new MybatisTest());
            t.start();
        }
    }
}
