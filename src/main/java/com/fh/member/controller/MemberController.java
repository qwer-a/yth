package com.fh.member.controller;

import com.fh.common.Ignore;
import com.fh.common.MemberAnnotation;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.member.service.MemberService;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    //验证姓名
    @RequestMapping("checkUserByName")
    @Ignore
    private ServerResponse checkMemberName(String name){
        return memberService.checkMemberName(name);
    }

    //验证电话号码
    @RequestMapping("checkPhone")
    @Ignore
    private ServerResponse checkPhone(String phone){
        return memberService.checkPhone(phone);
    }

    //注册
    @RequestMapping("register")
    @Ignore
    public ServerResponse register(Member member){
        return memberService.register(member);
    }

    //登录
    @RequestMapping("login")
    @Ignore
    public ServerResponse login(Member member){
        return memberService.login(member);
    }

    //判断是否登录
    @RequestMapping("againLogin")
    public ServerResponse againLogin(HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        if (member == null){
            return ServerResponse.error();
        }
        return ServerResponse.success();
    }

    //退出页面
    @RequestMapping("exitPages")
    public ServerResponse exitPages(HttpServletRequest request){
        String token = (String) request.getSession().getAttribute(SystemConstant.TOKEN_KEY);
        RedisUtil.deleteKeyValue(SystemConstant.TOKEN_KEY+token);
        //清除session中用户信息
        request.getSession().removeAttribute(SystemConstant.SESSION_KEY);
        //清除session中的token
        request.getSession().removeAttribute(SystemConstant.TOKEN_KEY);
        return ServerResponse.success();
    }

    //查询数据
    @RequestMapping("queryMemberList")
    @Ignore
    public ServerResponse queryMemberList(){
        return memberService.queryMemberList();
    }

    //回显
    @RequestMapping("toUpdate")
    @Ignore
    public ServerResponse toUpdate(Integer id){
        return memberService.toUpdate(id);
    }

    //修改
    @RequestMapping("updateMember")
    @Ignore
    public ServerResponse updateMember(Member member){
        return memberService.updateMember(member);
    }

    //删除
    @RequestMapping("deleteMember")
    @Ignore
    public ServerResponse deleteMember(Integer id){
        return memberService.deleteMember(id);
    }


    //查询当前登录的用户信息
    @RequestMapping("queryMember")
    public ServerResponse queryMember(HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        if (member == null){
            return ServerResponse.error();
        }
        return ServerResponse.success(member);
    }

    //设为默认地址
    @RequestMapping("updateAddress")
    @Ignore
    public ServerResponse updateAddress(Integer id){
        return memberService.updateAddress(id);
    }

    //默认地址
    @RequestMapping("moAddress")
    @Ignore
    public ServerResponse moAddress(Integer id){
        return memberService.moAddress(id);
    }




}
