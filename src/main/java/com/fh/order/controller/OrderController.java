package com.fh.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.common.Idempotent;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.order.model.Order;
import com.fh.order.service.OrderService;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //下订单
    @RequestMapping("goOrder")
    @Idempotent
    public ServerResponse goOrder(String products, String areaId , Integer payType, HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        List<Cart> productList = JSONObject.parseArray(products, Cart.class);
        return orderService.goOrder(productList,areaId,payType,member);

    }

    //获取幂等性的mtoken
    @RequestMapping("getMToken")
    public ServerResponse getMToken(){
        String mtoken = UUID.randomUUID().toString();
        RedisUtil.setKeyValue(mtoken,mtoken);
        return ServerResponse.success(mtoken);
    }

    //查询订单信息
    @RequestMapping("queryOrderList")
    @Ignore
    public ServerResponse queryOrderList(Integer memberId){
        return orderService.queryOrderList(memberId);
    }

}
