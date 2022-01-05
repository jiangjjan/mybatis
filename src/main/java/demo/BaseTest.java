package demo;

import demo.mapper.CountryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.PropertyConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class BaseTest {

    protected static SqlSession sqlSession;
    protected static CountryMapper mapper;

    @BeforeClass
    public static void before() throws IOException {

        try (InputStream is = CountryMapperTest.class.getClassLoader().getResourceAsStream("demo\\config\\log4j.properties");) {
            Properties properties = new Properties();
            properties.load(is);
            PropertyConfigurator.configure(properties);
            InputStream resourceAsStream = Resources.getResourceAsStream("demo/config/mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
            sqlSession = sqlSessionFactory.openSession();
            mapper = sqlSession.getMapper(CountryMapper.class);
        }

    }

    @AfterClass
    public static void after() {
        log.info("close sql session");
        sqlSession.close();
    }
}
