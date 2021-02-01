package com.baizhi.wxh.service;

import com.baizhi.wxh.annotation.AddCache;
import com.baizhi.wxh.annotation.AddLog;
import com.baizhi.wxh.annotation.DelCache;
import com.baizhi.wxh.dao.AdminMapper;
import com.baizhi.wxh.entity.Admin;
import com.baizhi.wxh.entity.AdminExample;
import com.baizhi.wxh.util.Md5Utils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

@Service("adminService")
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin queryOne(String username) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andUsernameEqualTo(username);
        return adminMapper.selectOneByExample(adminExample);
    }

    @Override
    @AddCache
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = new HashMap<>();
        RowBounds bounds = new RowBounds((page - 1) * rows, rows);
        map.put("page",page);
        int records = adminMapper.selectCount(null);
        map.put("records",records);
        map.put("rows",adminMapper.selectByRowBounds(null,bounds));
        if(records%rows == 0){
            map.put("total",records/rows);
        }else {
            map.put("total",records/rows+1);
        }

        return map;
    }

    @Override
    @Transactional
    @DelCache
    public void modifyStatus(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    @Transactional
    @AddLog(value = "添加管理员信息")
    @DelCache
    public void register(Admin admin) {
        admin.setId(UUID.randomUUID().toString());
        admin.setStatus("1");
        String salt = Md5Utils.getSalt(8);
        admin.setSalt(salt);
        admin.setPassword(Md5Utils.getMd5Code(salt+admin.getPassword()));
        adminMapper.insert(admin);
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "修改管理员信息")
    public void modify(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    @Transactional
    @DelCache
    @AddLog(value = "删除管理员信息")
    public void remove(String id) {
        adminMapper.deleteByPrimaryKey(id);
    }
}
