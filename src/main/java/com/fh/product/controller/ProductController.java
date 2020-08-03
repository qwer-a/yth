package com.fh.product.controller;

import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //通过是否是热销查询数据
    @RequestMapping("queryProductByIsHot")
    @Ignore
    public ServerResponse queryProductByIsHot(){
        return productService.queryProductByIsHot();
    }

    //查询所有数据
    @RequestMapping("queryProductList")
    @Ignore
    public ServerResponse queryProductList(){
        return productService.queryProductList();
    }

    //分页查询
    //currentPage  当前页数
    //pageSize     每页条数
    @RequestMapping("queryProductListByPage")
    @Ignore
    public ServerResponse queryProductListByPage(Long currentPage, Long pageSize){

       return  productService.queryProductListByPage(currentPage,pageSize);
    }
}
