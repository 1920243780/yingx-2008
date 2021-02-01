package com.baizhi.wxh.dao;

import com.baizhi.wxh.entity.Video;
import com.baizhi.wxh.po.VideoPO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {
    List<VideoPO> queryByReleaseTime();
}