package com.example.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.app.filter.AdminAuthFilter;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Override
    public Validator getValidator() {
        var validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Bean
    MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("validation");
        messageSource.setDefaultEncoding("UTF-8"); 
        return messageSource;
    }
    
    //  Admin認証用フィルタの有効化
    @Bean
    FilterRegistrationBean<AdminAuthFilter> adminAuthFilter() {
        var bean = new FilterRegistrationBean<AdminAuthFilter>(new AdminAuthFilter());
        bean.addUrlPatterns("/botanicals/*");
        return bean;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
    .addResourceLocations("file:///C:/Users/zd3N10/uploads/");
    }

}
