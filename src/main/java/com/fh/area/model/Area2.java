package com.fh.area.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_area2")
public class Area2 {

    @TableId(value = "areaId2",type = IdType.AUTO)
    private Integer areaId2;

    private String areaName2;

    public Integer getAreaId2() {
        return areaId2;
    }

    public void setAreaId2(Integer areaId2) {
        this.areaId2 = areaId2;
    }

    public String getAreaName2() {
        return areaName2;
    }

    public void setAreaName2(String areaName2) {
        this.areaName2 = areaName2;
    }
}
