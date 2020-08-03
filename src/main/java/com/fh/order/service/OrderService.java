package com.fh.order.service;

import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;
import com.fh.order.model.Order;

import java.util.List;

public interface OrderService {
    ServerResponse goOrder(List<Cart> productList, String areaId, Integer payType, Member member);

    ServerResponse queryOrderList(Integer memberId);
}
