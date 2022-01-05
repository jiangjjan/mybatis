package demo;

import demo.model.Country;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InterceptorTest extends BaseTest{

    @Test
    public void selectAllPlus(){
        RowBounds rowBounds = new RowBounds(0,3);
        List<Country> countries = mapper.selectAll(rowBounds);
        log.info("res is :{}",countries);
    }

    @Test
    public void selectByCountry(){
        List<String> names = new ArrayList<>();
        names.add("中国");
        names.add("美国");
        names.add("英国");
        RowBounds rowBounds = new RowBounds(0,1);
        List<Country> countries = mapper.listCountryByNames(names,rowBounds);
        log.info("res is :{}",countries);
    }
}
