package com.baizhi.wxh.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ExcelTarget("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Excel(name = "id")
    private String id;
    @Excel(name = "昵称")
    private String nickName;
    @Excel(name = "电话号码")
    private Integer phone;
    @Excel(name = "头像",type = 2)
    private String picImg;
    @Excel(name = "签名")
    private String brief;
    @Excel(name = "创建时间",format = "yyyy-MM-dd")
    private Date createDate;
    @Excel(name = "状态")
    private Integer status;
    @Excel(name = "微信号")
    private String vxNum;
}
