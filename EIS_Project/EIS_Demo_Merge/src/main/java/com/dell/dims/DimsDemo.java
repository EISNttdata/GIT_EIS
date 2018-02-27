package com.dell.dims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
//@ComponentScan
//@org.springframework.context.annotation.Configuration
//@EnableAutoConfiguration
public class DimsDemo extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(DimsDemo.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/eis/**")
                // .addResourceLocations("file:ext-resources/").setCachePeriod(0);
                .addResourceLocations("file:WebContent/WEB-INF/static/")
                .setCachePeriod(0);
    }

}
