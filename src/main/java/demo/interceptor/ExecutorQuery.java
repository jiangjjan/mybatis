package demo.interceptor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;

/**
 * 拦截器
 * 针对 Executor  query 接口实现拦截
 */
@Slf4j
@Intercepts(@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class ExecutorQuery implements Interceptor {

    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("properties {}", properties);
        log.info("[intercept] {}", invocation);
        Object target = invocation.getTarget();
        log.info("[target] {}", target);
        Method method = invocation.getMethod();
        log.info("[method] {}", method);
        Object[] args = invocation.getArgs();
        log.info("[args] {}", Arrays.toString(args));
        MappedStatement ms = (MappedStatement) args[0];
        Object param = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        log.info("rowBounds is default :{}", rowBounds == RowBounds.DEFAULT);
        Object proceed = invocation.proceed();
        log.info("[proceed] {}", proceed);
        ms.getResultSets();
        return proceed;
    }

    //获取xml中配置的属性值
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
