package com.baizhi.wxh.test;

import com.baizhi.wxh.dao.CateMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCateDao {
    @Autowired
    private CateMapper cateMapper;

   /* @Test
    public void selectByPage(){
        List<Cate> cates = cateMapper.selectByPage(1, 2);
        for (Cate cate : cates) {
            System.out.println(cate);
        }
    }*/
}
