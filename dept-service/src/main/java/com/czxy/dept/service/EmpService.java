package com.czxy.dept.service;

import com.czxy.dept.dao.DeptMapper;
import com.czxy.dept.dao.EmpMapper;
import com.czxy.dept.domain.Dept;
import com.czxy.dept.domain.Emp;
import com.czxy.dept.util.GetRandomCodeUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/10/27.
 */
@Service
@Transactional
public class EmpService {

    @Resource
    private EmpMapper empMapper;
    @Resource
    private DeptMapper deptMapper;

    public PageInfo<Emp> pagequery(Integer page,Integer rows,String ename){
        Example example = new Example(Emp.class);
        Example.Criteria criteria = example.createCriteria();
        if(!"".equals(ename)&&ename!=null){
            criteria.andLike("ename","%"+ename+"%");
        }
        List<Emp> list = empMapper.selectByExample(example);
        for (Emp thisEmp:list){
            Dept dept = deptMapper.selectByPrimaryKey(thisEmp.getDeptId());
            thisEmp.setDept(dept);
        }
        return new PageInfo<>(list);
    }

    public Integer save(Emp emp){
        Dept dept = deptMapper.selectByPrimaryKey(emp.getDeptId());
        emp.setDept(dept);
       return empMapper.insert(emp);
    }


    public void update(Emp emp){
        Dept dept = deptMapper.selectByPrimaryKey(emp.getDeptId());
        //先查询所有
        List<Emp> list = empMapper.selectAll();
        //获取到用户原来的密码
        for (Emp thisEmp:list){
          //赋值给当前用户
            //todo 一定要记住修改(set)后 需更新(update)对象到数据库  我在这个坑里呆了好久。。
            emp.setPassword(thisEmp.getPassword());
        }

        emp.setDept(dept);
        empMapper.updateByPrimaryKey(emp);
    }

    public void delete(String ids){
        String[] idss = ids.split(",");
        for(String thisId:idss){
            empMapper.deleteByPrimaryKey(thisId);
        }

    }


}
