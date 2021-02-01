package com.baizhi.wxh.service;

import java.util.HashMap;

public interface LogService {
    HashMap<String,Object> queryByPage(Integer page,Integer rows);
}
