package com.fh.area.controller;



import com.fh.area.service.AreaService;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("areas")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping("queryList")
    @Ignore
    public ServerResponse queryList(){
        return  areaService.queryList();
    }

    @RequestMapping("queryList2")
    @Ignore
    public ServerResponse queryList2(){
        return  areaService.queryList2();
    }

    @RequestMapping("queryList3")
    @Ignore
    public ServerResponse queryList3(){
        return  areaService.queryList3();
    }


}
