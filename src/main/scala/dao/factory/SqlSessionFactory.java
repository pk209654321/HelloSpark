package dao.factory;

import bean.collect.TotalUserActionInfo;
import conf.MapperFactory;
import mapper.*;
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

    public static ScreeningUserInfoMapper getScreeningUserInfoMapper(){
        return sqlSession.getMapper(ScreeningUserInfoMapper.class);
    }
    public static NewcomBagMapper getNewcomBagMapper(){
        return  sqlSession.getMapper(NewcomBagMapper.class);
    }
    public static RegisterInfoMapper getRegisterInfoMapper(){
        return  sqlSession.getMapper(RegisterInfoMapper.class);
    }
    public static LoginDataMapper getLoginDataMapper(){
        return sqlSession.getMapper(LoginDataMapper.class);
    }
    public static LoginResultMapper getLoginResultMapper(){
        return  sqlSession.getMapper(LoginResultMapper.class);
    }
    public static TotalUserActionInfoMapper getTotalUserActionInfoMapper(){
        return sqlSession.getMapper(TotalUserActionInfoMapper.class);
    }

    public static AccountDetailMapper getAccountDetailMapper(){
        return MapperFactory.createMapper(AccountDetailMapper.class,"business");
    }

}
