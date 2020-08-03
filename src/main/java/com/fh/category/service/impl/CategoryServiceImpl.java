package com.fh.category.service.impl;

import com.fh.category.mapper.CategoryMapper;
import com.fh.category.service.CategoryService;
import com.fh.common.ServerResponse;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl  implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    //三级查询
    @Override
    public ServerResponse queryCategoryList() {
        //判断缓存中是否存在该数据
        Boolean exist = RedisUtil.exists1(SystemConstant.REDIS_CATEGORY_KEY);
        if(exist){
            //存在

        }
        List<Map<String ,Object>>  allList =   categoryMapper.queryCategoryList();
        List<Map<String ,Object>>  parentList = new ArrayList<Map<String, Object>>();
        for (Map  map : allList) {
            if(map.get("pid").equals(0)){
                parentList.add(map);
            }
        }
        selectChildren(parentList,allList);
        return ServerResponse.success(parentList);
    }

    public void selectChildren(List<Map<String ,Object>>  parentList, List<Map<String ,Object>>  allList){
        for (Map<String, Object> pmap : parentList) {
            List<Map<String ,Object>>  childrenList = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> amap : allList) {
                if(pmap.get("id").equals(amap.get("pid"))){
                    childrenList.add(amap);
                }
            }
            if(childrenList!=null && childrenList.size()>0){
                pmap.put("children",childrenList);
                selectChildren(childrenList,allList);
            }

        }
    }




}
