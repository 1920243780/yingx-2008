package com.baizhi.wxh.service;

import com.baizhi.wxh.entity.City;
import com.baizhi.wxh.entity.PageDto;
import com.baizhi.wxh.entity.User;

import java.util.List;

public interface UserService {
    PageDto queryByPage(Integer curPage,Integer pageSize);

    List<User> queryAll();

    List<City> queryBySexAndCity(String sex);

    User queryOne(String id);

    String register(User user);

    void modify(User user);

    void remove(String id);

    void modifyStatus(User user);
}
