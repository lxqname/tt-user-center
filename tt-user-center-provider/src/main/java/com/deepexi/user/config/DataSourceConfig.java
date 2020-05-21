package com.deepexi.user.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 描述。
 * <p>
 * <br>==========================
 * <br> 公司：滴普科技
 * <br> 开发：hudong@deepexi.com
 * <br> 版本：1.0
 * <br> 创建时间：2018/5/2 10:06
 * <br>==========================
 */
@Configuration
@MapperScan("com.deepexi.user.mapper")
public class DataSourceConfig {

    public static final String CLOSE = "close";
    public static final String SPRING_DATASOURCE = "spring.datasource";

    @Bean(name = "dataSource", destroyMethod = CLOSE)
    @ConfigurationProperties(prefix = SPRING_DATASOURCE)
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

}