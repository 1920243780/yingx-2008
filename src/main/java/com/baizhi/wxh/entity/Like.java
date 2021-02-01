package com.baizhi.wxh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yx_like")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like implements Serializable {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "like_time")
    private Date likeTime;
    @Column(name = "video_id")
    private String videoId;
}