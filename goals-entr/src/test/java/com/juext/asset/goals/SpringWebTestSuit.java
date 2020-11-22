package com.juext.asset.goals;

import com.juext.asset.goals.config.*;
import com.juext.asset.goals.controller.AccountController;
import com.juext.asset.goals.facade.AccountFacadeImpl;
import com.juext.asset.goals.mapper.AccountMapper;
import com.juext.asset.goals.service.AccountServiceImpl;
import com.juext.asset.goals.spec.config.*;
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
