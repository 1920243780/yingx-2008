package com.baizhi.wxh.service;

import com.baizhi.wxh.dao.LogMapper;
import com.baizhi.wxh.entity.Log;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service("logService")
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class LogServiceImpl implements LogService{
    @Autowired
    private LogMapper logMapper;

    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        int records = logMapper.selectCount(new Log());
        map.put("records",records);
        if(records%rows == 0){
            map.put("total",records/rows);
        } else{
            map.put("total",records/rows+1);
        }
        RowBounds bounds = new RowBounds((page-1)*rows,rows);
        List<Log> logList = logMapper.selectByRowBounds(null,bounds);
        map.put("rows",logList);
        return map;
    }
}
