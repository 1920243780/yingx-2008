package com.baizhi.wxh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "yx_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cate implements Serializable {
    @Id
    private String id;

    @Column(name = "cateName")
    private String cateName;
    @Column(name = "levels")
    private Integer levels;
    @Column(name = "parentId")
    private String parentId;
}