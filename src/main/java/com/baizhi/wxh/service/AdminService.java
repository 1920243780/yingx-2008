package com.baizhi.wxh.service;

import com.baizhi.wxh.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    Admin queryOne(String username);

    HashMap<String,Object> queryByPage(Integer page, Integer rows);

    void modifyStatus(Admin admin);

    void register(Admin admin);

    void modify(Admin admin);

    void remove(String id);
}
