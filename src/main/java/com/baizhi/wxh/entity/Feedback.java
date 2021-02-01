package com.baizhi.wxh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yx_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements Serializable {
    private String id;

    private String title;

    private String content;

    private String userId;

    private Date createTime;
}