package cn.net.colin.common.config;

import cn.net.colin.common.util.DynamicDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源bean的配置类
 * Created by sxf on 2020-3-1.
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.profiles", name = "active",havingValue="dynamic")
public class MultipleDataSourceConfig {
    @Bean("db1")
    @ConfigurationProperties("spring.datasource.druid.db1")
    public DataSource dataSourceOne(){
//        return DruidDataSourceBuilder.create().build();
        return new DruidDataSource();
    }
    @Bean("db2")
    @ConfigurationProperties("spring.datasource.druid.db2")
    public DataSource dataSourceTwo(){
        return new DruidDataSource();
    }

    /**
     * 设置动态数据源，通过@Primary 来确定主DataSource
     * @return
     */
    @Bean
    @Primary
    public DataSource createDynamicDataSource(@Qualifier("db1") DataSource db1, @Qualifier("db2") DataSource db2){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(db1);
        //配置多数据源
        Map<Object, Object> map = new HashMap<>();
        map.put("db1",db1);
        map.put("db2",db2);
        dynamicDataSource.setTargetDataSources(map);
        return  dynamicDataSource;
    }
}
