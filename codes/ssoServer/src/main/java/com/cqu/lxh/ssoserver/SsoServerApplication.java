package com.cqu.lxh.ssoserver;

import com.cqu.lxh.ssoserver.service.SSOServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SsoServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(SsoServerApplication.class, args);
    }
// -- Mvc configuration ---------------------------------------------------

    @Bean
    WebMvcConfigurer createWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(SSOServerService.CLIENTA_HOST_URL)
                        .allowedOrigins(SSOServerService.CLIENTB_HOST_URL)
                        .allowedOrigins(SSOServerService.SERVER_URL_PREFIX)
                        .allowedMethods("GET", "POST");
            }
        };
    }
}
