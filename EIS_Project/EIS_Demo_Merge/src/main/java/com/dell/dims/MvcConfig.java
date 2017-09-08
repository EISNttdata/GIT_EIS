package com.dell.dims;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/eis/**")
				// .addResourceLocations("file:ext-resources/").setCachePeriod(0);
				.addResourceLocations("file:WebContent/WEB-INF/static/")
			//	.addResourceLocations("file:webapp/static/")
				.setCachePeriod(0);
	}
}
