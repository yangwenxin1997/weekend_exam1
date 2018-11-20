package com.czxy.dept.controller;

import com.czxy.dept.domain.Dept;
import com.czxy.dept.service.DeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/10/27.
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    @GetMapping("/findAllDept")
    public List<Dept> findAllDept(){
      List<Dept> list = deptService.findAllDept();
      return list;
    }


}
