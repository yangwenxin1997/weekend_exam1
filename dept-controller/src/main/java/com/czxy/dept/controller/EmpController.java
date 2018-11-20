package com.czxy.dept.controller;

import com.czxy.dept.domain.Emp;
import com.czxy.dept.service.EmpService;
import com.czxy.dept.util.GetRandomCodeUtil;
import com.czxy.dept.util.MailUtil;
import com.czxy.dept.vo.EasyUIResult;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.HTTP;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/10/27.
 */
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Resource
    private EmpService empService;
    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/pagequery")
    public ResponseEntity<EasyUIResult<Emp>> pagequery(Integer page,Integer rows,String ename){
        PageInfo<Emp> pageInfo = empService.pagequery(page, rows,ename);
        EasyUIResult result = new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
        return new ResponseEntity<EasyUIResult<Emp>>(result, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Emp emp){
        String randomPw = GetRandomCodeUtil.randomPassword();
        emp.setPassword(randomPw);
        Integer successFlag = empService.save(emp);
        if(successFlag==1) {
            try {
                String activeCode = RandomStringUtils.randomNumeric(32);
                redisTemplate.opsForValue().set(emp.getPhone(), activeCode, 24, TimeUnit.HOURS);
                String activeUrl = "http://localhost:8028/index.html";
                String text = "您的账户已开通，初始密码为" + randomPw + "</br><a href='" + activeUrl + "'>点击链接前往登陆</a>。";
                MailUtil.sendMail(emp.getMail(), "用户账号初始密码", text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return new ResponseEntity<String>("添加成功",HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody Emp emp){
        empService.update(emp);
        return new ResponseEntity<String>("修改成功", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(String ids){
        empService.delete(ids);
        return new ResponseEntity<String>("删除成功", HttpStatus.OK);
    }






}
