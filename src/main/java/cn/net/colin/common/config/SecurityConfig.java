package cn.net.colin.common.config;

import cn.net.colin.service.sysManage.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * @Package: cn.net.colin.common.config
 * @Author: sxf
 * @Date: 2020-3-4
 * @Description: 安全配置
 * 开启方法注解支持，我们设置prePostEnabled = true是为了后面能够使用hasRole()这类表达式
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //需要和生成TokenBasedRememberMeServices的密钥相同
    private static final String KEY="colin";
    @Autowired
    private ISysUserService sysUserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 该方法是重写了WebSecurityConfigurerAdapter中的方法
     * 用于：自定义SpringSecurity配置信息
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.authorizeRequests()
                //静态文件允许你访问
                .antMatchers("/assets/**","/bootstrap/**","/css/**","/image/**","/js/**","/webjars/**").permitAll()
                //所有的请求需要认证即登陆后才能访问
                .anyRequest().authenticated()
                .and()
                //form表单验证
                .formLogin().loginPage("/").permitAll()
                //设置默认登陆成功跳转的页面
                .defaultSuccessUrl("/main")
                //登陆失败的请求
                .failureUrl("/loginerror")
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                //                开启cookie保存用户数据
                .rememberMe()
                .rememberMeServices(getRememberMeServices()) // 必须提供
                //设置cookie私钥
                .key(KEY)
                //                处理异常，拒绝访问就重定向403页面
                .and().exceptionHandling().accessDeniedPage("/error");
//                .and().csrf().disable();
    }


    /**
     * 该方法是重写了WebSecurityConfigurerAdapter中的方法
     * 用于：指定认证对象的来源
     * @param auth
     * @throws Exception
     */
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserService).passwordEncoder(passwordEncoder());
    }


    /**
     * 如果要设置cookie过期时间或其他相关配置，请在下方自行配置
     */
    private TokenBasedRememberMeServices getRememberMeServices() {
        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices(KEY, sysUserService);
        services.setCookieName("remember-cookie");
        services.setTokenValiditySeconds(100); // 默认14天
        return services;
    }

}
