package com.wuminggao.blog.config;

import com.wuminggao.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author wuminggao
 * @create 2020-08-01-上午12:50
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")//拦截admin下的所有访问请求
                .excludePathPatterns(Arrays.asList("/admin", "/admin/login"));//排除掉的路径，不登录也可以访问
    }
}
