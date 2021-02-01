package com.baizhi.wxh.test;

import com.baizhi.wxh.YingxApplication;
import com.baizhi.wxh.dao.UserDao;
import com.baizhi.wxh.entity.City;
import com.baizhi.wxh.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = YingxApplication.class)
@RunWith(SpringRunner.class)
public class TestUserDao {
    @Resource
    private UserDao userDao;

    @Test
    public void test(){
        /*List<UserVo> list = userDao.selectBySexAndDate("男");
        for (UserVo userVo : list) {
            System.out.println(userVo);
        }*/

        List<City> list = userDao.selectBySexAndCity("男");
        for (City city : list) {
            System.out.println(city);
        }
    }

    @Test
    public void selectAll(){
        List<User> list = userDao.selectByPage(1,2);
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void insert(){
        userDao.insert(new User("3","33",333,"3.jpg","3333",null,null,"333221"));
    }

    @Test
    public void update(){
        userDao.update(new User("3","44",444,"2.jpg","3333",null,null,"333221"));
    }
}
