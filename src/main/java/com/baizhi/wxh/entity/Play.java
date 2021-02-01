package com.baizhi.wxh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "yx_play")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Play implements Serializable {
    @Id
    private String id;
    @Column(name = "video_id")
    private String videoId;
    @Column(name = "play_count")
    private Integer playCount;
}