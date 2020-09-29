package com.example.springmanager;

import com.example.springmanager.utils.LoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class SpringManagerApplication implements WebMvcConfigurer //extends SpringBootServletInitializer
{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringManagerApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
//      return application.sources(SpringManagerApplication.class);
//      }
}
