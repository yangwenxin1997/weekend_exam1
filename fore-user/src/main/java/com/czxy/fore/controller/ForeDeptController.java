package com.czxy.fore.controller;

import com.czxy.dept.domain.Emp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/10/27.
 */
@RestController
@RequestMapping("/foredept")
public class ForeDeptController {

    @Resource
    private RestTemplate restTemplate;

    /**
     * 查询所有
     * @return
     * todo 需要注意前台controller与后台controller返回值类型需一致
     */
    @GetMapping("/pagequery")
    public ResponseEntity<String> queryPromotionByPage(Integer page, Integer rows, String ename){
        System.out.println("fore sname:            "+ ename);
        if(ename!=null&&!"".equals(ename)){
            String url = "http://localhost:8026/emp/pagequery?page="+page+"&rows="+rows+"&ename="+ename;
            return restTemplate.getForEntity(url ,String.class);
        }
        String url = "http://localhost:8026/emp/pagequery?page="+page+"&rows="+rows;
        return restTemplate.getForEntity(url ,String.class);
    }

    //查询下拉框
    @GetMapping("/findAllDept")
    public ResponseEntity<String> findAllGrade(){
        String url = "http://localhost:8026/dept/findAllDept";
        return restTemplate.getForEntity(url ,String.class);
    }


    @PostMapping("/save")
    public ResponseEntity<String> save(Emp emp){
        System.out.println("fore添加"+emp.toString());
        String url = "http://localhost:8026/emp/save";
        return restTemplate.postForEntity(url ,emp,String.class);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(String ids){
        String url = "http://localhost:8026/emp/delete?ids="+ids;
        restTemplate.delete(url);
        return new ResponseEntity<String>("删除成功", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(Emp emp){
        String url = "http://localhost:8026/emp/update";
        return restTemplate.postForEntity(url ,emp,String.class);
    }













}
