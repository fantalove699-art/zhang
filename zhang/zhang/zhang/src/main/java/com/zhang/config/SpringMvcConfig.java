package com.zhang.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@ComponentScan("com.zhang.controller")
@EnableWebMvc
public class SpringMvcConfig {

    // 1. 配置 FTL 文件存放的路径和编码（这是后厨的图纸库）
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        // 告诉系统，FTL文件全都放在这个目录下
        configurer.setTemplateLoaderPath("/WEB-INF/ftl/index/");
        configurer.setDefaultEncoding("UTF-8");
        return configurer;
    }

    // 2. 配置 FTL 视图解析器（这是大堂经理的导航仪）
    @Bean
    public FreeMarkerViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        // 自动给 Controller 返回的字符串加上 .ftl 后缀
        resolver.setSuffix(".ftl");
        // 解决中文乱码问题
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }
}