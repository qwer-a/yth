package com.fh.cart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.cart.service.CartService;
import com.fh.common.MemberAnnotation;
import com.fh.common.ServerEnum;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductService productService;

    //登录直接添加购物车
    @Override
    public ServerResponse joinCart(Integer productId, int count,Member member) {

        //判断数据库是否有该商品
        //通过id查询数据
        Product product = productService.queryProductById(productId);
        if (product == null){
            return ServerResponse.error(ServerEnum.PRODUCT_NOT_EXIT);
        }

        //判断该商品是否上架
        if (product.getStatus() == 2){
            return ServerResponse.error(ServerEnum.PRODUCT_NOT_UP);
        }

        //判断购物车是否有该商品
        //根据商品id查询redis里面是否有该商品
        Boolean exists = RedisUtil.exists(SystemConstant.CART_KEY+member.getId(), product.getId().toString());
        //如果redis不存在该商品
        if (!exists){
            Cart cart = new Cart();
            cart.setProductId(productId);
            cart.setCount(count);
            cart.setFilePath(product.getFilePath());
            cart.setName(product.getName());
            cart.setPrice(product.getPrice());
            cart.setStatus(product.getStatus());
            cart.setMemberId(member.getId());
            RedisUtil.hset(SystemConstant.CART_KEY+member.getId(), productId.toString(), JSONObject.toJSONString(cart));
        }else {
            //如果redis里面有该商品，在此基础上数量相加
            //根据商品id获取redis里面的数据
            String hget = RedisUtil.hget(SystemConstant.CART_KEY+member.getId(), productId.toString());
            Cart cart = JSONObject.parseObject(hget, Cart.class);
            //改数量
            cart.setCount(cart.getCount()+count);
            //改过之后存到redis中
            RedisUtil.hset(SystemConstant.CART_KEY+member.getId(), productId.toString(), JSONObject.toJSONString(cart));

        }

        return ServerResponse.success();
    }

}
