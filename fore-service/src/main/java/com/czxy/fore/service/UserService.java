package com.czxy.fore.service;

import com.czxy.dept.domain.Emp;
import com.czxy.fore.dao.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/10/27.
 */
@Service
@Transactional
public class UserService {
    @Resource
    private UserMapper userMapper;

    public int modifyPassword(Emp emp) {
        int flag;
        Emp thisEmp = findByTelephone(emp.getPhone());
        if(thisEmp!=null){
            flag=1;
            thisEmp.setPassword(emp.getPassword());
            userMapper.updateByPrimaryKey(thisEmp);
        }else{
            flag=0;
        }

        return flag;
    }

    public Emp findByTelephone(String phone) {
        Example example = new Example(Emp.class);
        example.createCriteria().andEqualTo("phone", phone);
        Emp emp = (Emp) userMapper.selectOneByExample(example);
        return emp;
    }

    public Emp findUserByTelephoneAndPassword(String phone, String password) {
        Example example = new Example(Emp.class);
        example.createCriteria()
                .andEqualTo("phone", phone)
                .andEqualTo("password", password);
        Emp emp = userMapper.selectOneByExample(example);
        return emp;

    }



}
