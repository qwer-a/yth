package com.fh.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.common.ServerResponse;
import com.fh.product.mapper.ProductMapper;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    //通过是否是热销查询数据
    @Override
    public ServerResponse queryProductByIsHot() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isHot",1);

        List<Product> products = productMapper.selectList(queryWrapper);

        return ServerResponse.success(products);
    }

    //查询所有数据
    @Override
    public ServerResponse queryProductList() {
        List<Product> products = productMapper.selectList(null);
        return ServerResponse.success(products);
    }

    //分页查询
    @Override
    public ServerResponse queryProductListByPage(Long currentPage, Long pageSize) {

        IPage<Product> iPage = new Page(currentPage,pageSize);
        IPage<Product> iPageProduct = productMapper.selectPage(iPage, null);
        return ServerResponse.success(iPageProduct);
    }

    //通过id查询数据
    @Override
    public Product queryProductById(Integer productId) {
        return productMapper.selectById(productId);
    }

    //通过商品id和购物车的数量改变product商品的库存
    @Override
    public Long selectStoke(Integer id, int count) {
        return productMapper.selectStoke(id,count);
    }
}
