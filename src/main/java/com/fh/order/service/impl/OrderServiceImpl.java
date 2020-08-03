package com.fh.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.cart.model.Cart;
import com.fh.common.ServerEnum;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.order.mapper.OrderInfoMapper;
import com.fh.order.mapper.OrderMapper;
import com.fh.order.model.Order;
import com.fh.order.model.OrderInfo;
import com.fh.order.service.OrderService;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import com.fh.util.BigDecimalUtil;
import com.fh.util.IdUtil;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ProductService productService;

    //订单
    @Override
    public ServerResponse goOrder(List<Cart> productList, String areaId, Integer payType, Member member) {
        //先生成订单详情表数据
        //订单id
        String orderId = IdUtil.createId();

        //库存不足的集合
        List<String> stockNotFullList = new ArrayList<>();

        //存放订单详情表的集合
        List<OrderInfo> orderInfoList = new ArrayList<>();

        //价格
        BigDecimal totalPrice = new BigDecimal("0.00");


        for (Cart cart : productList) {
            //根据id查询商品数据
            Product product = productService.queryProductById(cart.getProductId());
            //判断商品库存是否充足
            if (product.getStock() < cart.getCount()){
                //库存不足
                stockNotFullList.add(cart.getName());
            }

                //库存充足
                //减库存，判断库存是否有剩余的
                //通过商品id和数量改变product商品的库存
                Long stokeCount = productService.selectStoke(product.getId(), cart.getCount());
                if (stokeCount == 1) {
                    //库存充足，生成订单详情表
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setOrderId(orderId);
                    orderInfo.setName(cart.getName());
                    orderInfo.setCount(cart.getCount());
                    orderInfo.setFilePath(cart.getFilePath());
                    orderInfo.setProductId(cart.getProductId());
                    orderInfo.setPrice(cart.getPrice());

                    //往集合中存放orderInfo数据
                    orderInfoList.add(orderInfo);

                    //总价格
                    BigDecimal cartPrice = BigDecimalUtil.mul(cart.getPrice().toString(), String.valueOf(cart.getCount()));
                    totalPrice = BigDecimalUtil.add(totalPrice,cartPrice);
                }else {
                    //库存不足
                    stockNotFullList.add(cart.getName());
                }
            }

        //先判断订单详情表中是否有库存不足的商品
        if (orderInfoList != null && orderInfoList.size() == productList.size()){

            //库存充足，订单详情表插入到数据库中
            for (OrderInfo orderInfo : orderInfoList) {
                orderInfoMapper.insert(orderInfo);
                //更新redis数据
                String hget = RedisUtil.hget(SystemConstant.CART_KEY + member.getId(), orderInfo.getProductId().toString());
                if (StringUtils.isNotBlank(hget)){
                    Cart cart = JSONObject.parseObject(hget, Cart.class);

                    //如果订单详情表商品数量大于等于购物车商品数量，移除redis购物车商品
                    if (cart.getCount()<=orderInfo.getCount()){
                        RedisUtil.hDel(SystemConstant.CART_KEY + member.getId(), orderInfo.getProductId().toString());
                    }else {
                        //否则更新redis中购物车中该商品的数量
                        cart.setCount(cart.getCount()-orderInfo.getCount());
                        RedisUtil.hset(SystemConstant.CART_KEY + member.getId(),cart.toString(),orderInfo.getProductId().toString());
                    }
                }

            }

            //生成订单
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setId(orderId);
            order.setAddressId(areaId);
            order.setCreateDate(new Date());
            order.setPayType(payType);
            order.setStatus(SystemConstant.ORDER_STATUS_START);
            order.setTotalPrice(totalPrice);
            order.setOrderInfoList(orderInfoList);

            orderMapper.insert(order);
            //把订单放到消息队列中

            return ServerResponse.success(orderId);
        }else {
            return ServerResponse.error(stockNotFullList);
        }

    }


    //查询订单信息
    @Override
    public ServerResponse queryOrderList(Integer memberId) {
        List<Order> orderList = new ArrayList<>();

        //根据id查询所有的订单
        List<Order> orders = orderMapper.queryOrderList(memberId);
        for (Order order : orders) {
            //根据订单id查询订单详情表的信息
            List<OrderInfo> orderInfoList = orderMapper.queryOrderInfoListById(order.getId());

            //给order表中的orderInfoList赋值
            List<OrderInfo> infoList = new ArrayList<>();
            for (OrderInfo orderInfo : orderInfoList) {

                //创建orderInfo对象并赋值
                OrderInfo orderInfo1 = new OrderInfo();
                orderInfo1.setPrice(orderInfo.getPrice());
                orderInfo1.setFilePath(orderInfo.getFilePath());
                orderInfo1.setCount(orderInfo.getCount());
                orderInfo1.setName(orderInfo.getName());

                infoList.add(orderInfo1);

            }

            //给order表赋值
            Order order1 = new Order();
            order1.setId(order.getId());
            order1.setAddressId(order.getAddressId());
            order1.setTotalPrice(order.getTotalPrice());
            order1.setStatus(order.getStatus());
            order1.setPayType(order.getPayType());
            order1.setCreateDate(order.getCreateDate());
            order1.setOrderInfoList(infoList);

            orderList.add(order1);

        }

        return ServerResponse.success(orderList);
    }
}
