package conf;

import mapper.Mapper;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * MapperFactory 创建一个Mapper实例 mapper
 */
public final class MapperFactory {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MapperFactory.class);
 
    /**
     * 通过datasource 创建一个Mapper 的实现类 mapper
     */
    @SuppressWarnings("unchecked")
    public static <T> T createMapper(Class<? extends Mapper> clazz, String datasource) {
        org.apache.ibatis.session.SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(datasource);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(clazz);
        return (T) MapperProxy.bind(mapper, sqlSession);
    }
 
    /**
     * Mapper代理: 执行 mapper 方法和关闭 sqlSession
     */
    private static class MapperProxy implements InvocationHandler {
        private Mapper mapper;
        private SqlSession sqlSession;
 
        private MapperProxy(Mapper mapper, SqlSession sqlSession) {
            this.mapper = mapper;
            this.sqlSession = sqlSession;
        }
 
        @SuppressWarnings("unchecked")
        private static Mapper bind(Mapper mapper, SqlSession sqlSession) {
            return (Mapper) Proxy.newProxyInstance(mapper.getClass().getClassLoader(),
                    mapper.getClass().getInterfaces(), new MapperProxy(mapper, sqlSession));
        }
 
        /**
         * 执行mapper方法并最终关闭sqlSession
         */
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object object = null;
            try {
                object = method.invoke(mapper, args);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            } finally {
                sqlSession.close();
            }
            return object;
        }
    }
 
    /**
     * 获取数据源 datasource 的 SqlSessionFactory
     */
    private static org.apache.ibatis.session.SqlSessionFactory getSqlSessionFactory(String datasource) {
        return SqlSessionFactory.getSqlSessionFactory(datasource);
    }
}