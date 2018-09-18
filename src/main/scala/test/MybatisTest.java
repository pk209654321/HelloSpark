package test;

import bean.Role;
import bean.login.LoginData;
import conf.MapperFactory;
import dao.factory.SqlSessionFactory;
import mapper.AccountDetailMapper;
import mapper.LoginDataMapper;
import mapper.RoleMapper;
import mapper.TotalUserActionInfoMapper;
import org.apache.ibatis.session.SqlSession;
import util.mybatis.SqlSessionFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
       /* LoginDataMapper business = MapperFactory.createMapper(LoginDataMapper.class, "business");
        Map<String,Object> map=new HashMap<>();
        List<LoginData> loginDatas = business.selectLoginDataList(map);
        System.out.println(loginDatas);*/
        //channelID = channelDao.checkRelationship("XXXXX", 123456);
        AccountDetailMapper accountDetailMapper = SqlSessionFactory.getAccountDetailMapper();
        Map<String,Object> map=new HashMap<>();
        accountDetailMapper.selectAccountDetailList(map);
        TotalUserActionInfoMapper totalUserActionInfoMapper = SqlSessionFactory.getTotalUserActionInfoMapper();
        totalUserActionInfoMapper.selectTotalUserActionInfoList(map);

    }
}
