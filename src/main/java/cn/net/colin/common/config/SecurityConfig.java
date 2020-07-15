package cn.net.colin.common.config;

import cn.net.colin.common.component.MyAccessDeniedHandler;
import cn.net.colin.common.component.MyAuthenticationEntryPoint;
import cn.net.colin.filter.JwtVerifyFilter;
import cn.net.colin.filter.JwtLoginFilter;
import cn.net.colin.filter.RequestURIFilter;
import cn.net.colin.service.sysManage.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * @Package: cn.net.colin.common.config
 * @Author: sxf
 * @Date: 2020-3-4
 * @Description: 安全配置
 * 开启方法注解支持，我们设置prePostEnabled = true，支持spring表达式注解 ，是为了后面能够使用hasRole()这类表达式
 *
 * securedEnabled，SpringSecurity提供的注解，@Secured({"ROLE_ADMIN","ROLE_PRODUCT"})//SpringSecurity注解
 * 基于SpringBoot的spring-boot-starter-security
 * 我们可以看到自动配置类中导入了WebSecurityEnablerConfiguration，
 * 而WebSecurityEnablerConfiguration上已经加了 @EnableWebSecurity注解
 * 所以其实在这里我们是可以不用自己添加的
 *
 * @Import({SpringBootWebSecurityConfiguration.class, WebSecurityEnablerConfiguration.class, SecurityDataConfiguration.class})
 * public class SecurityAutoConfiguration {
 *
 * @EnableWebSecurity
 * public class WebSecurityEnablerConfiguration {
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //需要和生成TokenBasedRememberMeServices的密钥相同
    private static final String KEY="colin";
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private RsaKeyProperties prop;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtVerifyFilter jwtVerifyFilter(){
        return new JwtVerifyFilter();
    }

    @Bean
    public RequestURIFilter requestURIFilter(){
        return new RequestURIFilter();
    }

    @Bean
    public AccessDeniedHandler myAccessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }
    @Bean
    public MyAuthenticationEntryPoint myAuthenticationEntryPoint() {
        return new MyAuthenticationEntryPoint();
    }

    /**
     * 该方法是重写了WebSecurityConfigurerAdapter中的方法
     * 用于：自定义SpringSecurity配置信息
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http
            .cors()
            .and()
            //允许iframe加载同源的资源
            .headers().frameOptions().sameOrigin()
            //.headers().frameOptions().disable()
            .and()
//            .csrf().disable()
            //可以针对项目中对外提供的接口（基于jwt+rsa认证的）可在这里设置忽略csrf
            .csrf().ignoringAntMatchers("/auth/login","/hello/*","/common/uploadSingle","/common/uploadMany","/ueditor/config").and()
            .authorizeRequests()
            //允许访问的路径，但是依然会走spring security内部流程
            .antMatchers("/","/login","/loginerror","/authException","/error","/common/sessionInvalid",
                    "/articleManage/articleView/*","/articleManage/comment/*","/test/*","/api/*").permitAll()
            //所有的请求需要认证即登陆后才能访问
            .anyRequest().authenticated()
            .and()
            //form表单验证
            .formLogin().loginPage("/login").permitAll()
            //设置默认登陆成功跳转的页面
            .defaultSuccessUrl("/main")
            //登陆失败的请求
            .failureUrl("/loginerror")
            .and()
            .logout().logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .and()
            .sessionManagement()
                .invalidSessionUrl("/common/sessionInvalid")
            .and()
            //                开启cookie保存用户数据
            .rememberMe()
            .rememberMeServices(getRememberMeServices()) // 必须提供
            //设置cookie私钥
            .key(KEY)
            //处理没有访问权限情况
            .and().exceptionHandling().accessDeniedHandler(myAccessDeniedHandler()).authenticationEntryPoint(myAuthenticationEntryPoint())
            .and()
            .addFilter(new JwtLoginFilter(super.authenticationManager(),prop))
            .addFilterAfter(jwtVerifyFilter(),JwtLoginFilter.class)
            .addFilterAfter(requestURIFilter(),JwtVerifyFilter.class);
    }


    /**
     * 该方法是重写了WebSecurityConfigurerAdapter中的方法
     * 用于：让静态资源不走springsecurity的过滤器
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/druid/**");
        web.ignoring().antMatchers("/favicon.ico");
        web.ignoring().antMatchers("/assets/**");
        web.ignoring().antMatchers("/bootstrap/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/image/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/plugin/**");
        web.ignoring().antMatchers("/json/**");
        web.ignoring().antMatchers("/uploadfile/**");
        //Swagger2
        web.ignoring(). antMatchers("/swagger-ui.html")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**");
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
