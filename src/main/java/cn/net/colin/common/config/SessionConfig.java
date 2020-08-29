package cn.net.colin.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Package: cn.net.colin.common.config
 * @Author: sxf
 * @Date: 2020-6-18
 * @Description: spirngsession 配置类,结合redis可实现多模块间session共享，配合Nginx可以实现负载均衡项目间的session共享
 */
@Configuration
//启用ResisSession存储
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800)
public class SessionConfig {
}
