package com.fh.area.service.impl;

import com.fh.area.mapper.Area2Mapper;
import com.fh.area.mapper.Area3Mapper;
import com.fh.area.mapper.AreaMapper;
import com.fh.area.model.Area;
import com.fh.area.model.Area2;
import com.fh.area.model.Area3;
import com.fh.area.service.AreaService;
import com.fh.common.ServerResponse;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Resource
    private AreaMapper areaMapper;

    @Resource
    private Area2Mapper area2Mapper;
    @Resource
    private Area3Mapper area3Mapper;

    public ServerResponse queryList() {
        List<Area> areas = areaMapper.selectList(null);
        return ServerResponse.success(areas);
    }

    @Override
    public ServerResponse queryList2() {
        List<Area2> area2s = area2Mapper.selectList(null);
        return ServerResponse.success(area2s);
    }

    @Override
    public ServerResponse queryList3() {
        List<Area3> areas3s = area3Mapper.selectList(null);
        return ServerResponse.success(areas3s);
    }


}
