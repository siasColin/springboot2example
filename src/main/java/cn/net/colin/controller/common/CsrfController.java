package cn.net.colin.controller.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiSort;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Package: cn.net.colin.controller.common
 * @Author: sxf
 * @Date: 2020-8-31
 * @Description: 用于获取csrf令牌
 *      浏览器直接访问：http://192.168.0.135:8081/csrf  即可看到效果
 *      前后端分离时，前端可通过该接口获取到CsrfToken信息，在发送post请求时可携带上CsrfToken，从而通过springsecurity的csrf验证
 *      主要是该项目目前没有做前后端分离，同时又想支持前后端分离，故提供该接口。如果是纯前后端分离，直接禁用springsecurity的csrf即可
 */
@RestController
@ApiSort(value = 0)
@Api(tags = "csrf令牌接口",consumes = "application/x-www-form-urlencoded")
public class CsrfController {

    @GetMapping("/csrf")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取csrf令牌", notes = "返回csrf令牌信息",
            consumes = "application/x-www-form-urlencoded",produces = "application/json")
    public CsrfToken csrf(@ApiIgnore CsrfToken token) {
        return token;
    }
}
