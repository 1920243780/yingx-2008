package com.baizhi.wxh.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.wxh.entity.PageDto;
import com.baizhi.wxh.entity.User;
import com.baizhi.wxh.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("export")
    @ResponseBody
    public HashMap<String,String> export(){
        List<User> users = userService.queryAll();
        HashMap<String, String> map = new HashMap<>();
        for (User user : users) {
            String picImg = user.getPicImg();
            String newImg = "D:\\idea\\ideaProjects\\yingx_wangxh\\src\\main\\webapp\\upload\\" + picImg;
            user.setPicImg(newImg);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("yingxApp用户信息表","用户"),
                User.class, users);
        try {
            FileOutputStream outputStream = new FileOutputStream("D:\\yingx用户信息表.xls");
            workbook.write(outputStream);
            map.put("message","导出成功");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("message","导出失败");
        }
        return map;
    }


    @RequestMapping("updateStatus")
    @ResponseBody
    public void updateStatus(User user){
        userService.modifyStatus(user);
    }

    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile picImg,String id,HttpSession session){
        try {
            if(picImg != null){
                String realPath = session.getServletContext().getRealPath("/upload");
                File dir = new File(realPath);
                if(!dir.exists())
                    dir.mkdir();

                String newPath = UUID.randomUUID().toString() + picImg.getOriginalFilename();
                picImg.transferTo(new File(realPath, newPath));
                User user = new User();
                user.setId(id);
                user.setPicImg(newPath);
                userService.modify(user);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @RequestMapping("operation")
    @ResponseBody
    public String operation(String oper, User user, HttpSession session){
        String id = null;
        try {
            //文件上传真实路径
            String realPath = session.getServletContext().getRealPath("/upload");
            if(oper.equals("add")){
                return userService.register(user);
            }else if(oper.equals("edit")){
                if(user.getPicImg().equals("")){
                    user.setPicImg(null);
                    userService.modify(user);
                    return null;
                }else {
                    userService.modify(user);
                    return user.getId();
                }
            }else {
                User delUser = userService.queryOne(user.getId());
                File delFile = new File(realPath,delUser.getPicImg());
                if(delFile.exists())
                    delFile.delete();
                userService.remove(user.getId());
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("ajaxQueryAll")
    public PageDto ajaxQueryAll(Integer page,Integer rows){
        return userService.queryByPage(page,rows);
    }
}
