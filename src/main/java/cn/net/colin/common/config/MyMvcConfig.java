package cn.net.colin.common.config;

import cn.net.colin.common.component.MyLocaleResolver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2020-2-27.
 */
@Configuration
@EnableSwagger2
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
                //在使用CORS方式跨域时，浏览器只会返回以下默认头部header;使用exposedHeaders可以返回自定义header
                .exposedHeaders("Authorization","Refresh_token")
                .allowedHeaders("*");// #允许访问的头信息,*表示全

    }

    @Bean
    public Docket createRestApi() {
        ParameterBuilder authorizationParameterBuilder = new ParameterBuilder();
        authorizationParameterBuilder
                .parameterType("header") //参数类型支持header, cookie, body, query etc
                .name("Authorization") //参数名
                .defaultValue("Bearer ") //默认值
                .description("token")
                .modelRef(new ModelRef("string"))//指定参数值的类型
                .required(true).build(); //非必需，这里是全局配置，然而在登陆的时候是不用验证的
        ParameterBuilder refreshTkenParameterBuilder = new ParameterBuilder();
        refreshTkenParameterBuilder
                .parameterType("header") //参数类型支持header, cookie, body, query etc
                .name("Refresh_token") //参数名
                .defaultValue("") //默认值
                .description("Refresh_token")
                .modelRef(new ModelRef("string"))//指定参数值的类型
                .required(false).build(); //非必需，这里是全局配置，然而在登陆的时候是不用验证的
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(authorizationParameterBuilder.build());
        aParameters.add(refreshTkenParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(aParameters)
                .apiInfo(apiInfo())
                .select()
                //为有@Api注解的Controller生成API文档
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //为有@ApiOperation注解的方法生成API文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //为当前配置的包下controller生成API文档
//                .apis(RequestHandlerSelectors.basePackage("cn.net.colin.controller.test"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                .description("API 描述")
                // 创建人信息
                .contact(new Contact("Colin",  "https://www.baidu.com",  "1540247870@qq.com"))
                .version("1.0")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:static/","file:static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:META-INF/resources/webjars/");
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
