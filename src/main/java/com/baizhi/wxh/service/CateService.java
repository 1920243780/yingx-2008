package com.baizhi.wxh.service;

import com.baizhi.wxh.entity.Cate;

import java.util.HashMap;

public interface CateService {
    HashMap<String,Object> queryOneByPage(Integer curPage,Integer pageSize);

    HashMap<String,Object> queryTwoByPage(Integer curPage,Integer pageSize,String parentId);

    void register(Cate cate);

    void modify(Cate cate);

    void remove(String id);
}
