package com.juext.asset.goals;

import com.zaxxer.hikari.HikariDataSource;
import org.featx.spec.feature.IdGenerate;
import org.featx.spec.feature.SnowflakeIdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Excepts
 * @since 2020/4/6 0:10
 */

@Configuration
@MapperScan("com.juext.asset.goals.mapper")
public class DataSourceConfiguration {

    @Value("${id.generate.snowflake.epoch:1}")
    private Long epoch;
    @Value("${id.generate.snowflake.date-center:1}")
    private Long dateCenterId;
    @Value("${id.generate.snowflake.machine:1}")
    private Long machineId;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public IdGenerate idGenerator() {
        return new SnowflakeIdWorker(epoch, dateCenterId, machineId);
    }
}
