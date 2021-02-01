package com.baizhi.wxh.controller;

import com.baizhi.wxh.entity.Admin;
import com.baizhi.wxh.entity.CreateValidateCode;
import com.baizhi.wxh.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("updateStatus")
    @ResponseBody
    public void updateStatus(Admin admin){
        adminService.modifyStatus(admin);
    }

    @RequestMapping("option")
    @ResponseBody
    public void option(String oper,Admin admin){
        if(oper.equals("add")){
            adminService.register(admin);
        }else if(oper.equals("edit")){
            adminService.modify(admin);
        }else{
            adminService.remove(admin.getId());
        }
    }

    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page, Integer rows){
        return adminService.queryByPage(page,rows);
    }

    @RequestMapping("login")
    public String login(String username,String password,String code,HttpSession session){
        try {
            String strCode = (String) session.getAttribute("code");
            if(strCode.equals(code)){
                Admin admin = adminService.queryOne(username);
                if(admin == null){
                    return "redirect:/login/login.jsp";
                }else if(admin.getPassword().equals(password)){
                    session.setAttribute("admin",admin);
                    return "redirect:/main/main.jsp";
                }else{
                    return "redirect:/login/login.jsp";
                }
            }else {
                return "redirect:/login/login.jsp";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/error.jsp";
        }
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }

    @RequestMapping("createImg")
    public void createImg(HttpSession session, ServletResponse response){
        try {
            CreateValidateCode vcode = new CreateValidateCode();

            String code = vcode.getCode();
            session.setAttribute("code",code);
            vcode.write(response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
