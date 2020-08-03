package com.fh.member.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_member")
public class Member {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private String phone;

    @TableField(exist = false)
    private String code;

    private Integer areaId;

    @TableField(exist = false)
    private String areaName;

    private Integer areaId2;

    @TableField(exist = false)
    private String areaName2;

    private Integer areaId3;

    @TableField(exist = false)
    private String areaName3;

    private Integer isNotAdd;

    public Integer getIsNotAdd() {
        return isNotAdd;
    }

    public void setIsNotAdd(Integer isNotAdd) {
        this.isNotAdd = isNotAdd;
    }

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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
