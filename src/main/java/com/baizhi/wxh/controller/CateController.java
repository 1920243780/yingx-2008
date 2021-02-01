package com.baizhi.wxh.controller;

import com.baizhi.wxh.entity.Cate;
import com.baizhi.wxh.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/cate")
public class CateController {
    @Autowired
    private CateService cateService;

    @RequestMapping("option")
    @ResponseBody
    public void option(String oper, Cate cate){
        try {
            if(oper.equals("add")){
                cateService.register(cate);
            }else if (oper.equals("edit")){
                cateService.modify(cate);
            }else {
                cateService.remove(cate.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("queryOneByPage")
    @ResponseBody
    public HashMap<String,Object> queryOneByPage(Integer page,Integer rows){
        return cateService.queryOneByPage(page, rows);
    }

    @RequestMapping("queryTwoByPage")
    @ResponseBody
    public HashMap<String,Object> queryTwoByPage(Integer page,Integer rows,String parentId){
        return cateService.queryTwoByPage(page,rows,parentId);
    }
}
