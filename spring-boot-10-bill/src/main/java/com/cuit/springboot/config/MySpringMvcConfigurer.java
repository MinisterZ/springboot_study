package com.cuit.springboot.config;

import com.cuit.springboot.component.MyLocaleResolver;
import com.cuit.springboot.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version JDK  1.8.151
 * @Author: Mirrors
 * @Description: Chengdu City
 */

@Configuration
public class MySpringMvcConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
         return    new WebMvcConfigurer(){
                //添加视图控制
                @Override                                                   //  找/main/login.html
                public void addViewControllers(ViewControllerRegistry registry) {
                    registry.addViewController("/").setViewName("main/login");
                    registry.addViewController("/index.html").setViewName("main/login");
                    registry.addViewController("/main.html").setViewName("main/index");

                }

//                //添加拦截器
//                 @Override
//                 public void addInterceptors(InterceptorRegistry registry) {
//                    registry.addInterceptor(new LoginHandlerInterceptor())
//                            //指定要拦截的请求 /**表示拦截所有请求
//                            .addPathPatterns("/**")
//                            //排除不需要拦截的请求路径
//                            .excludePathPatterns("/","/index.html","/login")
//                            //springboot2+之后需要将静态资源文件的访问路径也排除
//                            .excludePathPatterns("/css/*","/img/*","/js/*");
//             }
         };
    }

    //区域解析器
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

}
