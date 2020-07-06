package com.juext.shop.base.config;

import org.featx.spec.feature.ModelConvert;
import org.featx.spec.feature.ReflectionModelConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Excepts
 * @since 2020/6/21 12:32
 */
@Configuration
public class BeanConfiguration {
    @Bean
    ModelConvert reflectionModelConverter() {
        return BeanUtils.instantiateClass(ReflectionModelConverter.class);
    }
}
