package util.mybatis;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lenovo on 2018/8/1.
 */
public class SqlSessionFactoryUtil {
    private static SqlSessionFactory sqlSessionFactory = null;
    private static final Class CLASS_LOCK=SqlSessionFactoryUtil.class;

    private SqlSessionFactoryUtil() {

    }
    public static SqlSessionFactory initSqlSessionFactory() {
        String resource = "mybatis_config.xml";
        InputStream inputStream = null;
        try {
            inputStream= Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
            //Logger.getLogger(SqlSessionFactoryUtil.class.getName()).log(Priority.DEBUG, null, e);
        }
        synchronized (CLASS_LOCK) {
            if(sqlSessionFactory==null) {
                sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
            }
        }
        return sqlSessionFactory;
    }
    public static SqlSession openSqlSession(boolean boo) {
        if(sqlSessionFactory==null) {
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession(boo);
    }
}
