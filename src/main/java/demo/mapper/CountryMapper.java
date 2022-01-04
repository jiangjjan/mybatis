package demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import demo.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryMapper {

    List<Country> selectAll();

    /**
     * 查询一个实例,无结果时会返回null,(可以使用Optional包装返回结果),查询返回值为集合时,返回一个空集合
     * 使用when进行多种情况查询: 有id时,使用id进行查询;
     * 没有id时,使用code或者name进行查询,
     * 否则不查询;
     */
    Optional<Country> selectByCountry(Country p);

    int addOne(Country param);

    /**
     * 新增数据,并获取自增主键
     */
    int addOneAndGetAutoPK(Country p);


    /**
     * 新增数据,获取生成的主键
     */
    int addOneAndGetSelectKey(Country p);

    @Insert(" insert into country (country_name,country_code) values (#{countryName},#{countryCode})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addOneAndGetAutoPKByAnnotation(Country p);

    @Insert(" insert into country (country_name,country_code) values (#{countryName},#{countryCode})")
    @SelectKey(statement = "select Last_Insert_Id()", keyProperty = "id", before = false, resultType = Long.class)
    int addOneAndGetSelectKeyByAnnotation(Country p);

    /**
     * 不使用@Param注解,并且参数只有一个集合时, mybatis 会将参数存为map ,
     * {arg0=[英国, 中国], collection=[英国, 中国], list=[英国, 中国]}
     * <p>
     * 多参数时,参封装成Map key为 arg0,arg1(从0开始),param1,param2(从1开始)
     * 使用@Param注解时,会将 arg 对应的参数换为注解内的值 {names=[英国, 中国], arg1=xxx, param1=[英国, 中国], param2=xxx}
     * names为注解内的值
     * <p>
     * 总结:
     * 多参数与使用注解 时的参数形式类似,除了key会被替换为注解内得值
     */
    List<Country> listCountryByNames(@Param("names") List<String> names);

    /**
     * 不使用@Param注解,并且参数只有一个数组时, mybatis 会将参数存为map
     * {array=[Ljava.lang.String;@9f5c0d, arg0=[Ljava.lang.String;@9f5c0d}
     * <p>
     * 多参数时 {arg1=asd, arg0=[Ljava.lang.String;@39f68d, param1=[Ljava.lang.String;@39f68d, param2=asd}
     * <p>
     * 使用注解{arg1=asd, names=[Ljava.lang.String;@39f68d, param1=[Ljava.lang.String;@39f68d, param2=asd}
     */
    List<Country> arrayCountryByNames(@Param("names") String[] names);
}
