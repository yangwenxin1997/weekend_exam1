package com.czxy.dept.service;

import com.czxy.dept.dao.DeptMapper;
import com.czxy.dept.domain.Dept;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/10/27.
 */
@Service
@Transactional
public class DeptService {
    @Resource
    private DeptMapper deptMapper;

    public List<Dept> findAllDept(){
        List<Dept> list = deptMapper.selectAll();
        return list;
    }



}
