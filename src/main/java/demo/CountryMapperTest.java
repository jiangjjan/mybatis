package demo;

import demo.model.Country;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CountryMapperTest extends BaseTest {

    @Test
    public void selectAll() throws SQLException {
        DatabaseMetaData metaData = sqlSession.getConnection().getMetaData();
        System.out.println(metaData.getDatabaseProductName());
        List<Country> countries = mapper.selectAll();
        log.info("countries {}", countries);
    }

    @Test
    public void selectMoreParam() {
        Country c= new Country();
        c.setCountryCode("CN");
        List<Country> countries = mapper.selectMoreParam("中国",c);
        log.info("countries {}", countries);
    }

    @Test
    public void addOne() {
        Country p = new Country();
        p.setCountryCode("FR");
        p.setCountryName("法国");
        int res = mapper.addOne(p);
//        sqlSession.commit();
        log.info("addOne : res {} ;Country {}", res, p);
    }

    @Test
    public void addOneAndGetAutoPK() {
        Country p = new Country();
        p.setCountryCode("GB");
        p.setCountryName("英国");
        int res = mapper.addOneAndGetAutoPK(p);
//        sqlSession.commit();
        log.info("addOneAndGetAutoPK :res {}; Country {}", res, p);
    }


    @Test
    public void addOneAndGetSelectKey() {
        Country p = new Country();
        p.setCountryCode("GB");
        p.setCountryName("英国");
        int res = mapper.addOneAndGetSelectKey(p);
        log.info("addOneAndGetSelectKey :res {}; Country {}", res, p);
    }

    @Test
    public void addOneAndGetAutoPKByAnnotation() {
        Country p = new Country();
        p.setCountryCode("GB");
        p.setCountryName("英国");
        int res = mapper.addOneAndGetAutoPKByAnnotation(p);
        log.info("addOneAndGetAutoPKByAnnotation :res {}; Country {}", res, p);
    }

    @Test
    public void addOneAndGetSelectKeyByAnnotation() {
        Country p = new Country();
        p.setCountryCode("GB");
        p.setCountryName("英国");
        int res = mapper.addOneAndGetSelectKeyByAnnotation(p);
        log.info("addOneAndGetSelectKeyByAnnotation :res {}; Country {}", res, p);
    }


    @Test
    public void selectByCountry() {
        Country p = new Country();
        p.setCountryCode("GB");
        p.setCountryName("英国");
        Optional<Country> res = mapper.selectByCountry(p);
        log.info("selectByCountry : isPresent {};res {}; Country {}", res.isPresent(), res, p);

        Country p1 = new Country();
        Optional<Country> res1 = mapper.selectByCountry(p1);
        log.info("selectByCountry1 : isPresent {};res {}; Country {}", res1.isPresent(), res1, p1);

        Country p2 = new Country();
        p2.setId(1L);
        p2.setCountryCode("GB");
        Optional<Country> res2 = mapper.selectByCountry(p2);
        log.info("selectByCountry2 : isPresent {};res {}; Country {}", res2.isPresent(), res2, p2);
    }

    @Test
    public void listCountryByNames() {
        List<String> names = new ArrayList<>();
        names.add("英国");
        names.add("中国");
        List<Country> res = mapper.listCountryByNames(names);
        log.info("listCountryByNames :res {}; names {}", res, names);
    }

    @Test
    public void arrayCountryByNames() {
        String[] names = {"英国", "中国"};
        List<Country> res = mapper.arrayCountryByNames(names);
        log.info("listCountryByNames :res {};", res);
    }

}
