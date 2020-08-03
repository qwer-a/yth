package com.fh.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ServerResponse;
import com.fh.member.mapper.MemberMapper;
import com.fh.member.model.Member;
import com.fh.member.service.MemberService;
import com.fh.util.JwtUtil;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    //验证姓名
    @Override
    public ServerResponse checkMemberName(String name) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Member member = memberMapper.selectOne(queryWrapper);
        if (member == null){
            return ServerResponse.success();
        }
        return ServerResponse.error("用户已存在");
    }

    //验证电话号码
    @Override
    public ServerResponse checkPhone(String phone) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        Member member = memberMapper.selectOne(queryWrapper);
        if (member == null){
            return ServerResponse.success();
        }
        return ServerResponse.error("电话号码已存在");
    }

    //注册
    @Override
    public ServerResponse register(Member member) {
        String redisCode = RedisUtil.getKeyValue(member.getPhone());
        if (redisCode == null){
            return ServerResponse.error("验证码失效！！");
        }
        if (!redisCode.equals(member.getCode())){
            return ServerResponse.error("验证码不正确");
        }

        //新增
        memberMapper.insert(member);
        return ServerResponse.success();
    }

    //登录
    @Override
    public ServerResponse login(Member member) {
        //获取前台传来的数据
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",member.getName());
        queryWrapper.or();
        queryWrapper.eq("phone",member.getName());

        //获取数据库的数据
        Member memberDB = memberMapper.selectOne(queryWrapper);

        //判断
        if (memberDB == null){
            return ServerResponse.error("用户名或手机号码错误！");
        }

        if (!member.getPassword().equals(memberDB.getPassword())){
            return ServerResponse.error("密码错误！");
        }

        //生成token并传给前端
        String token = "";
        try {
            //加密
            String encodeUser = URLEncoder.encode(memberDB.getId().toString(), "utf-8");
            //生成token
            token = JwtUtil.sign(encodeUser);

            //往redis一个过期时间
            RedisUtil.expireTime(SystemConstant.TOKEN_KEY+token,token, SystemConstant.TOKEN_EXPIRY_TIME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ServerResponse.success(token);
    }

    //通过id查询用户数据
    @Override
    public Member queryMemberById(Integer id) {
        return memberMapper.selectById(id);
    }

    //查询数据
    @Override
    public ServerResponse queryMemberList() {
        List<Member> members = memberMapper.queryMemberList();
        return ServerResponse.success(members);
    }

    //回显
    @Override
    public ServerResponse toUpdate(Integer id) {
        Member member = memberMapper.toUpdate(id);
        return ServerResponse.success(member);
    }

    //修改
    @Override
    public ServerResponse updateMember(Member member) {
        memberMapper.updateMember(member);
        return ServerResponse.success();
    }

    //删除
    @Override
    public ServerResponse deleteMember(Integer id) {
        memberMapper.deleteMember(id);
        return ServerResponse.success();
    }

    //设为默认地址
    @Override
    public ServerResponse updateAddress(Integer id) {
         memberMapper.updateAddress(id);
         return ServerResponse.success();
    }

    //默认地址
    @Override
    public ServerResponse moAddress(Integer id) {
        memberMapper.moAddress(id);
        return ServerResponse.success();
    }
}
