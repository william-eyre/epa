package com.epa.epa;

import com.epa.epa.authentication.PermissionCheckInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EpaApplication implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new PermissionCheckInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/admin/**");
  }

  public static void main(String[] args) {
    SpringApplication.run(EpaApplication.class, args);
  }
}
