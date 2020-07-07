package com.juext.asset.goals.config;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Excepts
 * @since 2019/10/28 12:33
 */

//@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource(name = "fastJsonConverter")
    HttpMessageConverter fastJsonConverter;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, fastJsonConverter);
    }
}
