package com.zhang.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;
import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
public class ServletContainersInitConfig extends AbstractDispatcherServletInitializer {
    protected WebApplicationContext createServletApplicationContext(){
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringMvcConfig.class);
        return ctx;
    }
    protected String[] getServletMappings(){
        return new String[]{"/"};
    }
    protected WebApplicationContext createRootApplicationContext(){
        return null;
    }
    @Override
    protected Filter[] getServletFilters() {
        // 创建一个 Spring 提供的字符编码过滤器
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        // 强制设置请求的编码为 UTF-8
        filter.setEncoding("UTF-8");
        // 强制设置响应的编码也为 UTF-8
        filter.setForceEncoding(true);

        // 把这个过滤器注册到大门上
        return new Filter[]{filter};
    }
}
