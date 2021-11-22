package com.gao.community.config;

import com.gao.community.interceptor.LoginInterceptor;
import com.gao.community.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 通过对页面的拦截实现跳转功能，以及配置Login、User拦截器的拦截路径
 * @author: XiaoGao
 * @time: 2021/11/3 17:06
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //拦截器必须手动示例bean，否则拦截器中无法自动注入其他类
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
    @Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }

    String[] addPathPatterns={
            "/","/index.html"
    };
    String[] excludePathPatterns={
            "/redis/**","/rabbit/**","**.ico"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //配置登录拦截器
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns(addPathPatterns)
                .excludePathPatterns(excludePathPatterns);
        //配置用户拦截器
        registry.addInterceptor((userInterceptor()))
                .addPathPatterns("/**")
                .excludePathPatterns("/error","/static/**","/css/**","/js/**");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        System.out.println("拦截");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController(("/home/login")).setViewName("login");
    }
}
