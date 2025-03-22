package com.shadcn.identity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Configuring CORS");
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // Supports patterns with allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
