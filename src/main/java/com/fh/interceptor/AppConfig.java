package com.fh.interceptor;

import com.fh.resolver.MemberResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class AppConfig extends WebMvcConfigurationSupport {

    @Autowired
    private MemberResolver memberResolver;

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    public IdempotentInterceptor idempotentInterceptor(){
        return new IdempotentInterceptor();
    }


    //为 interceptor类型的处理器添加用户定义的处理器
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
       /* List<String> list= new ArrayList<String>();
        list.add("/member/checkUserByName");
        list.add("/member/checkPhone");
        list.add("/member/register");
        list.add("/member/login");
        list.add("/msg/getCode");
        list.add("/categorys/queryCategoryList");
        list.add("/products/queryProductByIsHot");
        list.add("/products/queryProductList");
        list.add("/products/queryProductListByPage");*/

        /*  /**匹配所有拦截路径  */
        //addPathPatterns：添加你要拦截的请求路径
        /*registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns(list);*/
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/swagger-resources/**","/webjars/**","/swagger-ui.html/**","/v2/**");
        registry.addInterceptor(idempotentInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/swagger-resources/**","/webjars/**","/swagger-ui.html/**","/v2/**");
    }

    @Override
    //参数解析器，被框架回调
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(memberResolver);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        //解决静态资源不能访问
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        //解决Swagger不能访问
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        //解决Swagger的js文件不能访问
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
