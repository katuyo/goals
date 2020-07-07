package com.juext.asset.goals;

import com.juext.asset.goals.config.*;
import com.juext.asset.goals.controller.AccountController;
import com.juext.asset.goals.facade.AccountFacadeImpl;
import com.juext.asset.goals.storage.DataSourceConfiguration;
import com.juext.asset.goals.storage.mapper.AccountMapper;
import com.juext.asset.goals.storage.service.AccountServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Excepts
 * @since 2020/7/7 9:47
 */

@SpringBootTest(classes = {
        DataSourceConfiguration.class, BeanConfiguration.class,
        FeignConfiguration.class, RedisConfiguration.class,
        JsonConverterConfiguration.class, WebMvcConfiguration.class,
        AccountController.class, AccountFacadeImpl.class, AccountServiceImpl.class, AccountMapper.class})
public abstract class SpringWebTestSuit {
}
