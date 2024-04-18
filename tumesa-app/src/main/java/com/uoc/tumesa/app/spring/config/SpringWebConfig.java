package com.uoc.tumesa.app.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@EnableWebMvc
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/assets/css/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS));
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/assets/js/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS));
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/assets/images/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS));
    }
}
