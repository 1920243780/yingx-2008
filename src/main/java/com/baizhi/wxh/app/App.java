package com.baizhi.wxh.app;

import com.baizhi.wxh.common.CommonResult;
import com.baizhi.wxh.po.VideoPO;
import com.baizhi.wxh.service.VideoService;
import com.baizhi.wxh.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/app")
public class App {
    @Autowired
    private VideoService videoService;

    @RequestMapping("queryByReleaseTime")
    public Object queryByReleaseTime(){
        try {
            List<VideoPO> videoPOS = videoService.queryByReleaseTime();
            return new CommonResult().success("100","查询成功",videoPOS);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult().faild();
        }
    }

    @RequestMapping("getPhoneCode")
    public HashMap<String,Object> getPhoneCode(String phone){
        HashMap<String, Object> map = new HashMap<>();
        String message = null;
        try {
            String salt = Md5Utils.getSalt(6);
            message = "ok";
            map.put("status","100");
            map.put("message",message);
            map.put("data",phone);
        }catch (Exception e){
            e.printStackTrace();
            message = "no";
            map.put("data",null);
            map.put("status","104");
            map.put("message",message);
        }

        return map;
    }
}
