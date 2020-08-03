package com.fh.product.service;

import com.fh.common.ServerResponse;
import com.fh.product.model.Product;

import java.util.List;

public interface ProductService {
    ServerResponse queryProductByIsHot();

    ServerResponse queryProductList();

    ServerResponse queryProductListByPage(Long currentPage, Long pageSize);

    Product queryProductById(Integer productId);

    Long selectStoke(Integer id, int count);
}
