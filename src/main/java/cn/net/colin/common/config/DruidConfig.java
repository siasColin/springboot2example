package cn.net.colin.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package: cn.net.colin.common.config
 * @Author: sxf
 * @Date: 2020-10-12
 * @Description: druid 配置类
 */
@Configuration
public class DruidConfig {

    @Value("${spring.datasource.url:}")
    private String dbUrl;

    @Value("${spring.datasource.username:}")
    private String username;

    @Value("${spring.datasource.password:}")
    private String password;
    @Value("${spring.datasource.driver-class-name:}")
    private String driverClassName;

    @Bean()
    @ConditionalOnProperty(prefix = "spring.datasource", name = "type",havingValue="com.alibaba.druid.pool.DruidDataSource")
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dbUrl);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driverClassName);
        return druidDataSource;
    }

    //配置Druid的监控
    //1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();

        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("resetEnable","false");
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
        initParams.put("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return  bean;
    }

    @Bean
    public WallFilter wallFilter(){
        WallConfig wc = new WallConfig ();
        // 解决 sql批量执行报错 java.sql.SQLException: sql injection violation, multi-statement not allow
        wc.setMultiStatementAllow(true);
        WallFilter wfilter = new WallFilter ();
        wfilter.setConfig(wc);
        return wfilter;
    }
}
