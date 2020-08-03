package com.fh.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.common.ServerResponse;
import com.fh.order.model.Order;
import com.fh.order.model.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> queryOrderList(Integer memberId);

    List<OrderInfo> queryOrderInfoListById(String id);
}
