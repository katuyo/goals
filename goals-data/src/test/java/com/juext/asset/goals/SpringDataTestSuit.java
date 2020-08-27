package com.juext.asset.goals;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.test.context.ContextConfiguration;

import java.nio.charset.StandardCharsets;

/**
 * @author Excepts
 * @since 2020/7/6 22:52
 */

//@SpringBootTest(classes = DataSourceConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@MybatisTest
@ContextConfiguration(classes = DataSourceConfiguration.class)
public abstract class SpringDataTestSuit {

    private final EasyRandomParameters parameters = new EasyRandomParameters()
            .charset(StandardCharsets.UTF_8);

    protected <T> T generate(Class<T> tClass) {
        EasyRandom easyRandom = new EasyRandom(parameters);
        return easyRandom.nextObject(tClass);
    }
}
