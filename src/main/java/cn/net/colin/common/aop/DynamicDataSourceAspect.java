package cn.net.colin.common.aop;

import cn.net.colin.common.util.DynamicDataSourceSwitcher;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by sxf on 2020-3-1.
 */
@Aspect
@Component
@Order(1) //需要加入切面排序
public class DynamicDataSourceAspect {
    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * 切入点只对@Service注解的类上的@DataSource方法生效
     * @param dataSourceAnnotation
     */
    @Pointcut(value="@within(org.springframework.stereotype.Service) && @annotation(dataSourceAnnotation)" )
    public void dynamicDataSourcePointCut(DataSourceAnnotation dataSourceAnnotation){}

    @Before(value = "dynamicDataSourcePointCut(dataSourceAnnotation)")
    public void switchDataSource(DataSourceAnnotation dataSourceAnnotation) {
        DynamicDataSourceSwitcher.setDataSource(dataSourceAnnotation.value());
    }

    /**
     * 切点执行完后 切换成主数据库
     * @param dataSourceAnnotation
     */
    @After(value="dynamicDataSourcePointCut(dataSourceAnnotation)")
    public void after(DataSourceAnnotation dataSourceAnnotation){
        DynamicDataSourceSwitcher.cleanDataSource();
    }
}