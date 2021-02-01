package com.baizhi.wxh.service;

import com.baizhi.wxh.annotation.AddCache;
import com.baizhi.wxh.annotation.AddLog;
import com.baizhi.wxh.annotation.DelCache;
import com.baizhi.wxh.dao.LikeMapper;
import com.baizhi.wxh.dao.PlayMapper;
import com.baizhi.wxh.dao.VideoMapper;
import com.baizhi.wxh.entity.*;
import com.baizhi.wxh.po.VideoPO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service("videoService")
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class VideoServiceImpl implements VideoService{
    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private PlayMapper playMapper;

    @Autowired
    private LikeMapper likeMapper;

    @Override
    @AddCache
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        int records = videoMapper.selectCount(null);
        map.put("records",records);
        if(records%rows==0)
            map.put("total",records/rows);
        else
            map.put("total",records/rows+1);
        RowBounds bounds = new RowBounds((page - 1) * rows, rows);

        List<Video> videoList = videoMapper.selectByRowBounds(new Video(), bounds);

        for (Video video : videoList) {
            String id = video.getId();

            PlayExample playExample = new PlayExample();
            playExample.createCriteria().andVideoIdEqualTo(id);
            Play play = playMapper.selectOneByExample(playExample);
            if(play != null){
                video.setPlayCount(play.getPlayCount());
            }else {
                video.setPlayCount(0);
            }

            LikeExample likeExample = new LikeExample();
            likeExample.createCriteria().andVideoIdEqualTo(id);
            int likeCount = likeMapper.selectCountByExample(likeExample);
            video.setLikeCount(likeCount);
        }

        map.put("rows",videoList);
        return map;
    }

    @Override
    @Transactional
    @DelCache
    public void modifyStatus(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "添加视频信息")
    public String register(Video video) {
        String uuid = UUID.randomUUID().toString();
        video.setId(uuid);
        video.setCreateTime(new Date());
        video.setStatus("1");
        videoMapper.insert(video);
        return uuid;
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "修改视频信息")
    public String modify(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
        return video.getId();
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "删除视频信息")
    public String remove(String id) {
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);
        Video video = videoMapper.selectOneByExample(example);
        videoMapper.deleteByPrimaryKey(id);
        return video.getVideoPath();
    }

    @Override
    @AddCache
    public List<VideoPO> queryByReleaseTime() {
        List<VideoPO> videoPOS = videoMapper.queryByReleaseTime();
        for (VideoPO videoPO : videoPOS) {
            String id = videoPO.getId();
            LikeExample example = new LikeExample();
            example.createCriteria().andVideoIdEqualTo(id);
            int likeCount = likeMapper.selectCountByExample(example);
            videoPO.setLikeCount(likeCount);
        }
        return videoPOS;
    }
}
