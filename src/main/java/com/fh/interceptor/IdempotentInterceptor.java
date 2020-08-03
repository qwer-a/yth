package com.fh.interceptor;

import com.fh.common.Idempotent;
import com.fh.common.Ignore;
import com.fh.common.MyException;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdempotentInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //自定义的注解，放过要拦截的路径
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(Idempotent.class)){
            return true;
        }

        //获取headers的mtoken
        String mtoken = request.getHeader("mtoken");
        if (StringUtils.isEmpty(mtoken)){
            throw new MyException("没有mtoken!");
        }

        //redis是否有mtoken
        Boolean exists1 = RedisUtil.exists1(mtoken);
        if (!exists1){
            throw new MyException("mtoken失效!");
        }

        //删除redis中的mtoken
        Long delToken = RedisUtil.deleteKey(mtoken);
        if (delToken == 0){
            throw new MyException("重复下单");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
