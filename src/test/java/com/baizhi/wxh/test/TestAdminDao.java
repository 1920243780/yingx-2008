package com.baizhi.wxh.test;

import com.baizhi.wxh.YingxApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = YingxApplication.class)
@RunWith(SpringRunner.class)
public class TestAdminDao {
    /*@Autowired
    private AdminDao adminDao;

    @Test
    public void selectOne(){
        Admin admin = adminDao.selectOne("admin");
        System.out.println(admin);
    }*/
}
