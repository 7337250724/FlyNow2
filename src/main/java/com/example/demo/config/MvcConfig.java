package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Intentionally empty - let HomeController handle root path
        // This prevents Spring from looking for static index.html
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure static resources but exclude root path
        // This ensures controllers are checked before static resources
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/");
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("classpath:/static/uploads/");
        
        // Handle specific static HTML files (like admin.html) but NOT index.html
        // This prevents Spring from looking for index.html as a welcome page
        registry.addResourceHandler("/admin.html", "/about.html", "/contact.html", 
                                   "/destination.html", "/hotel.html", "/blog.html", 
                                   "/main.html", "/blog-single.html")
                .addResourceLocations("classpath:/static/");
    }
}

