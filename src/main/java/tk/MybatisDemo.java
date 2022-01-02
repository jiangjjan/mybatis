package tk;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.PropertyConfigurator;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tk.mapper.CountryMapper;
import tk.model.Country;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MybatisDemo {

    private static SqlSession sqlSession;

    @BeforeClass
    public static void before() throws IOException {
        URL url = MybatisDemo.class.getClassLoader().getResource("tk\\config\\log4j.properties");
        PropertyConfigurator.configure(url);
        InputStream inputStream = Resources.getResourceAsStream("tk/config/mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void selectAll() {

        CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);

        List<Country> countries = mapper.selectAll();
        System.out.println(countries);
    }

    @AfterClass
    public static void after() {
        sqlSession.close();
    }

}
