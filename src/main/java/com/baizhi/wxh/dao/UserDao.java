package com.baizhi.wxh.dao;

import com.baizhi.wxh.entity.City;
import com.baizhi.wxh.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    List<User> selectByPage(@Param("curPage") Integer curPage,@Param("pageSize") Integer pageSize);

    List<User> selectAll();

    List<City> selectBySexAndCity(String sex);

    User selectOne(String id);

    int selectCount();

    void insert(User user);

    void update(User user);

    void delete(String id);
}
