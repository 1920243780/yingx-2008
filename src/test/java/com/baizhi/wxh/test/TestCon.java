package com.baizhi.wxh.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCon {
    @Autowired
    private DruidDataSource ds;

    @Test
    public void testCon() throws SQLException {
        System.out.println(ds.getConnection());
    }
}
