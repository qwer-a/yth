package com.fh.cart.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.cart.service.CartService;
import com.fh.common.Ignore;
import com.fh.common.MemberAnnotation;
import com.fh.common.ServerEnum;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController {

    @Autowired
    private CartService cartService;

    //登录直接添加购物车
    @RequestMapping("joinCart")
    public ServerResponse joinCart(Integer productId, int count, HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        return cartService.joinCart(productId,count,member);
    }

    //查询购物车
    @RequestMapping("queryCartProductCount")
    public ServerResponse queryCartProductCount(HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        List<String> stringList = RedisUtil.hGet(SystemConstant.CART_KEY + member.getId());
        long totalCount = 0;
        if (stringList != null && stringList.size() > 0){
            for (int i = 0; i < stringList.size(); i++) {
                Cart cart = JSONObject.parseObject(stringList.get(i), Cart.class);
                totalCount += cart.getCount();
            }
        }else {
            return ServerResponse.success(0);
        }

        return ServerResponse.success(totalCount);
    }

    //查询数据
    @RequestMapping("queryCartList")
    public ServerResponse queryCartList( HttpServletRequest request){
        Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        List<String> stringList = RedisUtil.hGet(SystemConstant.CART_KEY + member.getId());
        List<Cart> cartList = new ArrayList<>();
        if (stringList != null && stringList.size() > 0){
            for (int i = 0; i < stringList.size(); i++) {
                Cart cart = JSONObject.parseObject(stringList.get(i), Cart.class);
                cartList.add(cart);
            }
        }else {
            return ServerResponse.error(ServerEnum.CART_IS_NULL.getMsg());
        }

        return ServerResponse.success(cartList);
    }


    //删除
    @RequestMapping("deleteProduct")
    public ServerResponse deleteProduct(HttpServletRequest request,Integer productId){
        Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        RedisUtil.hDel(SystemConstant.CART_KEY+member.getId(), productId.toString());
        return ServerResponse.success();
    }

    //批量删除
    @RequestMapping("deleteBatch")
    public ServerResponse deleteBatch(HttpServletRequest request, @RequestParam("ids") List<String> ids){
        Member member = (Member) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        String [] strArray = ids.toArray(new String[ids.size()]);
        RedisUtil.hDelBatch(SystemConstant.CART_KEY+member.getId(),strArray);
        return ServerResponse.success();

    }

}
