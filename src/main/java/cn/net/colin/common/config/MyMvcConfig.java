package cn.net.colin.common.config;

import cn.net.colin.common.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by colin on 2020-2-27.
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**  java
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/","/user/login","/assets/**","/js/**","/css/**","/image/**","/bootstrap/**","/webjars/**");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
//        registry.addViewController("/main").setViewName("index");
    }

    /**
     * 解决跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置。
                .allowedOrigins("*")//开放哪些ip、端口、域名的访问权限
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")//开放哪些http方法，允许跨域访问
                .allowCredentials(true)//是否允许发送cookie信息
                .maxAge(3600)// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
                .allowedHeaders("*");// #允许访问的头信息,*表示全
    }

    /**
     * 国际化
     * @return
     */
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
}
