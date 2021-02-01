package com.baizhi.wxh.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.wxh.YingxApplication;
import com.baizhi.wxh.dao.UserDao;
import com.baizhi.wxh.entity.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = YingxApplication.class)
@RunWith(SpringRunner.class)
public class TestEasyPoi {

    @Resource
    private UserDao userDao;

    @Test
    public void testEasyPoi(){
        List<User> users = userDao.selectByPage(1, 5);
        for (User user : users) {
            String picImg = user.getPicImg();
            String newImg = "D:\\idea\\ideaProjects\\yingx_wangxh\\src\\main\\webapp\\upload\\" + picImg;
            user.setPicImg(newImg);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("yingxApp用户信息表","学生"),
                User.class, users);
        try {
            FileOutputStream outputStream = new FileOutputStream("D:\\yingx用户信息表.xls");
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
