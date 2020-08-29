package cn.net.colin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by sxf on 2020-3-1.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
    @Override
    protected Object determineCurrentLookupKey() {
//        logger.info("------------------当前数据源 {}", DynamicDataSourceSwitcher.getDataSource());
        return DynamicDataSourceSwitcher.getDataSource();
    }
}
