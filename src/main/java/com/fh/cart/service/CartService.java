package com.fh.cart.service;

import com.fh.common.MemberAnnotation;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    ServerResponse joinCart(Integer productId, int count,Member member);
}
