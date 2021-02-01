package com.baizhi.wxh.controller;

import com.baizhi.wxh.entity.City;
import com.baizhi.wxh.entity.UserVo;
import com.baizhi.wxh.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/echarts")
@RestController
public class EchartsController {
    @Resource
    private UserService userService;

    @RequestMapping("getUserRegirestChinaDatas")
    public List<UserVo> getUserRegirestChinaDatas(){
        List<City> boyCity = userService.queryBySexAndCity("男");
        List<City> girlCity = userService.queryBySexAndCity("女");

        List<UserVo> userVos = new ArrayList<>();

        userVos.add(new UserVo("小男生",boyCity));
        userVos.add(new UserVo("小姑娘",girlCity));

        return userVos;
    }

    @RequestMapping("getUserRegirestData")
    public HashMap<String,Object> getUserRegirestData(){
        /*HashMap<String, Object> map = new HashMap<>();
        List<UserVo> boyList = userService.queryBySexAndDate("男");
        List<String> months = new ArrayList<>();
        for (UserVo boy : boyList) {
            months.add(boy.getMonth());
            map.put("month",months);
        }
        List<UserVo> girlList = userService.queryBySexAndDate("女");
        map.put("boys",boyList);
        map.put("girls",girlList);
        return map;*/

       //数据查询 sql怎么写 数据封装
        HashMap<String, Object> map = new HashMap<>();

        map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
        map.put("boys",Arrays.asList(5, 200, 36, 10, 10, 200));
        map.put("girls",Arrays.asList(200, 20, 360, 100, 10, 20));

        /*
         * http  应用层协议   短连接
         * tcp/ip 网络层协议  长链接
         * */
        return map;
    }
}
