package com.fh.category.controller;

import com.fh.category.service.CategoryService;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categorys")
@Api(value = "分类接口",tags = {"分类接口"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    //查询
    @RequestMapping("queryCategoryList")
    @Ignore
    @ApiOperation(value = "查询分类")
    public ServerResponse queryCategoryList(){
        return categoryService.queryCategoryList();
    }

    @RequestMapping(name="add",method = RequestMethod.POST)
    @Ignore
    @ApiOperation(value = "添加分类", notes = "add")
    public ServerResponse add(@ApiParam(name="name",value = "用户名称")@RequestBody String name,
                              @ApiParam(name="age",value = "用户年龄",required = true) Integer age){
        /*this.name = name;
        this.age = age;*/

        System.out.println("name="+name+",age="+age);
        return ServerResponse.success();
    }
}
