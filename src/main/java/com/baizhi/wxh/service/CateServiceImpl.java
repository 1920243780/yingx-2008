package com.baizhi.wxh.service;

import com.baizhi.wxh.annotation.AddCache;
import com.baizhi.wxh.annotation.AddLog;
import com.baizhi.wxh.annotation.DelCache;
import com.baizhi.wxh.dao.CateMapper;
import com.baizhi.wxh.entity.Cate;
import com.baizhi.wxh.entity.CateExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service("cateService")
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class CateServiceImpl implements CateService{
    @Autowired
    private CateMapper cateMapper;

    @Override
    @AddCache
    public HashMap<String, Object> queryOneByPage(Integer curPage, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        CateExample cateExample = new CateExample();
        cateExample.createCriteria().andLevelsEqualTo(1);
        map.put("page",curPage);
        int records = cateMapper.selectCountByExample(cateExample);
        map.put("records",records);
        if(records%pageSize == 0){
            map.put("total",records/pageSize);
        }else {
            map.put("total",records/pageSize+1);
        }
        RowBounds rowBounds = new RowBounds((curPage-1)*pageSize,pageSize);
        List<Cate> cateList = cateMapper.selectByExampleAndRowBounds(cateExample, rowBounds);
        map.put("rows",cateList);
        return map;
    }

    @Override
    @AddCache
    public HashMap<String, Object> queryTwoByPage(Integer curPage, Integer pageSize, String parentId) {
        HashMap<String, Object> map = new HashMap<>();
        CateExample cateExample = new CateExample();
        cateExample.createCriteria().andParentidEqualTo(parentId);
        RowBounds rowBounds = new RowBounds((curPage-1)*pageSize,pageSize);
        map.put("page",curPage);
        int records = cateMapper.selectCountByExample(cateExample);
        map.put("records",records);
        List<Cate> cateList = cateMapper.selectByExampleAndRowBounds(cateExample, rowBounds);
        map.put("rows",cateList);
        if(records%pageSize == 0){
            map.put("total",records/pageSize);
        }else {
            map.put("total",records/pageSize+1);
        }
        return map;
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "添加类别信息")
    public void register(Cate cate) {
        cate.setId(UUID.randomUUID().toString());
        if(cate.getParentId() == null){
            cate.setLevels(1);
        }else {
            cate.setLevels(2);
        }
        cateMapper.insert(cate);
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "修改类别信息")
    public void modify(Cate cate) {
        cateMapper.updateByPrimaryKeySelective(cate);
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "删除类别信息")
    public void remove(String id) {
        cateMapper.deleteByPrimaryKey(id);
    }
}
