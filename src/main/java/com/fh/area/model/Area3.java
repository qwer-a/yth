package com.fh.area.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_area3")
public class Area3 {

    @TableId(value = "areaId3",type = IdType.AUTO)
    private Integer areaId3;

    private String areaName3;

    public Integer getAreaId3() {
        return areaId3;
    }

    public void setAreaId3(Integer areaId3) {
        this.areaId3 = areaId3;
    }

    public String getAreaName3() {
        return areaName3;
    }

    public void setAreaName3(String areaName3) {
        this.areaName3 = areaName3;
    }
}
