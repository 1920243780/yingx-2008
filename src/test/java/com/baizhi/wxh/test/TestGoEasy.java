package com.baizhi.wxh.test;

import com.alibaba.fastjson.JSON;
import com.baizhi.wxh.YingxApplication;
import com.baizhi.wxh.dao.UserDao;
import com.baizhi.wxh.entity.City;
import com.baizhi.wxh.entity.UserVo;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = YingxApplication.class)
@RunWith(SpringRunner.class)
public class TestGoEasy {
    @Resource
    private UserDao userDao;

    @Test
    public void test(){
        List<UserVo> userVos = new ArrayList<>();

        List<City> boyList = userDao.selectBySexAndCity("男");

        for (City city : boyList) {
            Integer value = city.getValue();
            city.setValue(value+200);
        }

        List<City> girlList = userDao.selectBySexAndCity("女");

        userVos.add(new UserVo("小男孩",boyList));
        userVos.add(new UserVo("小女孩",girlList));

        String jsonString = JSON.toJSONString(userVos);


        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-af99dc7b9e2c48ce9cf54bfd0237bb2e");
        goEasy.publish("my_channel", jsonString);
    }
}
