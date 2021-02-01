package com.baizhi.wxh.service;

import com.baizhi.wxh.entity.Video;
import com.baizhi.wxh.po.VideoPO;

import java.util.HashMap;
import java.util.List;

public interface VideoService {
    HashMap<String,Object> queryByPage(Integer page,Integer rows);

    void modifyStatus(Video video);

    String register(Video video);

    String modify(Video video);

    String remove(String id);

    List<VideoPO> queryByReleaseTime();
}
