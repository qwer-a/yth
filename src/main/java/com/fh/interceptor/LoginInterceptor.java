package com.fh.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.Ignore;
import com.fh.common.LoginException;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.member.service.MemberService;
import com.fh.util.JwtUtil;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //处理客户端传过来的自定义头信息
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-auth,mtoken,content-type");
        // 处理客户端发过来put,delete
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "PUT,POST,DELETE,GET");

        //自定义的注解，放过要拦截的路径
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(Ignore.class)){
            return true;
        }

        //获取请求头里面的token
        String token = request.getHeader("x-auth");

        //判断获取的token是否为空，如果为空，返回登录页面
        if (StringUtils.isEmpty(token)){
            throw new LoginException();
        }

        //验证token是否失效
        Boolean exists1 = RedisUtil.exists1(SystemConstant.TOKEN_KEY+token);
        if (!exists1){
            return false;
        }

        //验证token
         boolean verifyToken = JwtUtil.verify(token);
        if (verifyToken){
            //获取token中的信息
            String userId = JwtUtil.getUserId(token);
            //解密
            String idString = URLDecoder.decode(userId, "utf-8");
            Integer id = Integer.valueOf(idString);
            //通过id查询用户数据
            Member member = memberService.queryMemberById(id);

            //往客户端的session存入用户信息
            request.getSession().setAttribute(SystemConstant.SESSION_KEY,member);

            //往客户端的session存放token
            request.getSession().setAttribute(SystemConstant.TOKEN_KEY,token);

        }else {
            return false;
        }

        return true;
    }

}
