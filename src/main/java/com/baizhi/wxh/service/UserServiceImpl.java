package com.baizhi.wxh.service;

import com.baizhi.wxh.annotation.AddCache;
import com.baizhi.wxh.annotation.AddLog;
import com.baizhi.wxh.annotation.DelCache;
import com.baizhi.wxh.dao.UserDao;
import com.baizhi.wxh.entity.City;
import com.baizhi.wxh.entity.PageDto;
import com.baizhi.wxh.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    @Override
    @AddCache
    public PageDto queryByPage(Integer curPage,Integer pageSize) {
        PageDto page = new PageDto();
        page.setPage(curPage);
        page.setRecords(userDao.selectCount());
        if(userDao.selectCount()%pageSize == 0)
            page.setTotal(userDao.selectCount()/pageSize);
        else
            page.setTotal(userDao.selectCount()/pageSize+1);
        page.setRows(userDao.selectByPage(curPage,pageSize));
        return page;
    }

    @Override
    public List<User> queryAll() {
        return userDao.selectAll();
    }

    @Override
    public List<City> queryBySexAndCity(String sex) {
        return userDao.selectBySexAndCity(sex);
    }

    @Override
    public User queryOne(String id) {
        return userDao.selectOne(id);
    }

    @Override
    @Transactional
    @AddLog(value = "添加用户信息")
    @DelCache
    public String register(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        userDao.insert(user);
        return uuid;
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "修改用户信息")
    public void modify(User user) {
        userDao.update(user);
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "删除用户信息")
    public void remove(String id) {
        userDao.delete(id);
    }

    @Override
    @DelCache
    @Transactional
    public void modifyStatus(User user) {
        userDao.update(user);
    }
}
