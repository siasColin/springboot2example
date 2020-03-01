package cn.net.colin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 操作数据源
 * 利用ThreadLocal 将数据源与当前线程绑定，并提供get set方法
 * Created by sxf on 2020-3-1.
 */
public class DynamicDataSourceSwitcher {
    static Logger logger = LoggerFactory.getLogger(DynamicDataSourceSwitcher.class);

    public static final String db1 = "db1";
    public static final String db2 = "db2";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String name){
        logger.info("-------- 设置数据源数据源为 ：{} ", name);
        contextHolder.set(name);
    }
    public static String getDataSource(){
        return contextHolder.get();
    }
    public static void cleanDataSource(){
        contextHolder.remove();
    }
}
