package demo.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtils {
    public static void print(Object p) {
        log.info(" type is {};param : {}", p.getClass().getName(), p);
    }
}
