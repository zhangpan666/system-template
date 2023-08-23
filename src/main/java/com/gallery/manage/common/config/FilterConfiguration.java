package com.gallery.manage.common.config;

import com.gallery.manage.common.filter.SignFilter;
import com.gallery.manage.common.constants.ProjectConstant;
import com.light.config.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

//@Configuration
public class FilterConfiguration {


    @Bean
    public FilterRegistrationBean signFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new SignFilter());
        bean.addUrlPatterns(ProjectConstant.API_PREFIX);
        bean.setName("Api Sign Filter");
        bean.setOrder(2);
        bean.setEnabled(false);
        bean.addInitParameter("urlPatterns", ProjectConstant.API_PREFIX);
        return bean;
    }

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new LogFilter());
        bean.addUrlPatterns("/");
        bean.setName("log Filter");
        bean.setOrder(1);
        return bean;
    }


}
