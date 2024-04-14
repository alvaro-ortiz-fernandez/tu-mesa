package com.uoc.tumesa.app.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan({
    "com.uoc.tumesa.app.spring.web"
})
public class SpringWebConfig implements WebMvcConfigurer {

    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**")
                .addResourceLocations("/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/js/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/images/");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
