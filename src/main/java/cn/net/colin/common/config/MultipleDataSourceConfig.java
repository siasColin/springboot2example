package cn.net.colin.common.config;

import cn.net.colin.common.util.DynamicDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源bean的配置类
 * Created by sxf on 2020-3-1.
 */
@Configuration
public class MultipleDataSourceConfig {
    @Bean("db1")
    @ConfigurationProperties("spring.datasource.druid.db1")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }
    @Bean("db2")
    @ConfigurationProperties("spring.datasource.druid.db2")
    public DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
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


    //配置Druid的监控
    //1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();

        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("allow","");//默认就是允许所有访问
//        禁止访问
//        initParams.put("deny","192.168.15.21");

        bean.setInitParameters(initParams);
        return bean;
    }


    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return  bean;
    }
}
