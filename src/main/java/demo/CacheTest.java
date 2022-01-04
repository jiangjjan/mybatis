package demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.PropertyConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import demo.mapper.CountryMapper;
import demo.model.Country;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
public class CacheTest {


    private static SqlSessionFactory sqlSessionFactory;

    private static SqlSession baseSqlSession;

    private static CountryMapper countryMapper;

    @BeforeClass
    public static void before() throws IOException {
        try (InputStream is = CountryMapperTest.class.getClassLoader().getResourceAsStream("demo\\config\\log4j.properties");) {
            Properties properties = new Properties();
            properties.load(is);
            PropertyConfigurator.configure(properties);
            InputStream resourceAsStream = Resources.getResourceAsStream("demo/config/mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
            baseSqlSession = sqlSessionFactory.openSession();
            countryMapper = baseSqlSession.getMapper(CountryMapper.class);
        }
    }

    @Test
    public void sameSessionSameMapper() {
        Country country = new Country();
        country.setId(1L);
        log.info("start select");
        Optional<Country> country1 = countryMapper.selectByCountry(country);
        log.info("res {}", country1.orElse(null));
        log.info("start select");
        Optional<Country> country2 = countryMapper.selectByCountry(country);
        log.info("res {}", country2.orElse(null));


    }

    @Test
    public void sameSessionDifferMapper() {

        Country country = new Country();
        country.setId(1L);
        log.info("start select");
        Optional<Country> country1 = countryMapper.selectByCountry(country);
        log.info("res {}", country1.orElse(null));
        log.info("start select");
        log.info("before countryMapper hashcode {}", countryMapper.hashCode());
        countryMapper = baseSqlSession.getMapper(CountryMapper.class);
        log.info("after countryMapper hashcode {}", countryMapper.hashCode());
        Optional<Country> country2 = countryMapper.selectByCountry(country);
        log.info("res {}", country2.orElse(null));

    }

    /**
     * 不同的session 不会使用缓存
     */
    @Test
    public void differentSession() {

        Country country = new Country();
        country.setId(1L);
        log.info("start select");
        countryMapper = sqlSessionFactory.openSession().getMapper(CountryMapper.class);
        Optional<Country> country1 = countryMapper.selectByCountry(country);
        log.info("res {}", country1.orElse(null));
        log.info("start select");
        log.info("before countryMapper hashcode {}", countryMapper.hashCode());
        countryMapper = sqlSessionFactory.openSession().getMapper(CountryMapper.class);
        log.info("after countryMapper hashcode {}", countryMapper.hashCode());
        Optional<Country> country2 = countryMapper.selectByCountry(country);
        log.info("res {}", country2.orElse(null));

    }

    /**
     * session 不同时,缓存会导致数据不一致
     */
    @Test
    public void updateDataUseDifferentSqlSession1() {
        //查询一次,加入缓存
        List<Country> countries = countryMapper.selectAll();
       try( Scanner sc= new Scanner(System.in)){
           sc.next();
       }
    }

    @Test
    public void updateDataUseDifferentSqlSession2() {

    }

    @AfterClass
    public static void after() {
        log.info("close sql session");
        baseSqlSession.close();
    }

}
