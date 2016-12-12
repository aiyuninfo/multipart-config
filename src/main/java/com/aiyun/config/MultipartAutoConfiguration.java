package com.aiyun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;

/**
 * Created by zhaoy on 2016/12/7.
 * 解决spring boot最大只能上传1M文件的解决方案
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.http.multipart", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(MultipartProperties.class)
public class MultipartAutoConfiguration {
    @Autowired
    private MultipartProperties multipartProperties;

//    public MultipartAutoConfiguration(MultipartProperties multipartProperties) {
//        this.multipartProperties = multipartProperties;
//    }

    @Bean
    @ConditionalOnMissingBean
    public MultipartConfigElement multipartConfigElement() {
        this.multipartProperties.setMaxFileSize("-1");
        return this.multipartProperties.createMultipartConfig();
    }

    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean(MultipartResolver.class)
    public StandardServletMultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        multipartResolver.setResolveLazily(false);
        return multipartResolver;
    }
}

