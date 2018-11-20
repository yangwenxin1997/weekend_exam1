package com.czxy.fore.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.czxy.dept.domain.Emp;
import com.czxy.dept.util.MailUtil;
import com.czxy.dept.util.SmsUtil;
import com.czxy.fore.domain.VariableCode;
import com.czxy.fore.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/10/27.
 */
@RestController
@RequestMapping("/emp")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private HttpSession session;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private HttpServletRequest request;

    @GetMapping("/login")
    public ResponseEntity<String> login(String phone, String password, String checkcode) {
        String sessionValidateCode = (String) request.getSession().getAttribute("checkcode");
        if (sessionValidateCode == null) {
            return new ResponseEntity<String>("验证码失效", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!sessionValidateCode.equalsIgnoreCase(checkcode)) {
            return new ResponseEntity<String>("验证码错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Emp emp = userService.findUserByTelephoneAndPassword(phone, password);
        if (emp == null) {
            return new ResponseEntity<String>("用户名或密码不匹配", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        request.getSession().setAttribute("emp", emp);
        return new ResponseEntity<String>("登录成功", HttpStatus.OK);
    }


    @GetMapping("/sendSms")
    public ResponseEntity<Void> sendSms(String phone) {
        String randomCode = RandomStringUtils.randomNumeric(4);
        System.out.println("本次生成的验证码是：" + randomCode);
        //将验证码存入到redis中
        redisTemplate.opsForValue().set(phone , randomCode , 5, TimeUnit.MINUTES);
      //  session.setAttribute(phone, randomCode);
        String phonefour = phone.substring(7,10);

        //2 发送短信
        try {
            SendSmsResponse sendSmsResponse = SmsUtil.sendSms(phone, phonefour,randomCode,"小润发","666666");
            System.out.println(sendSmsResponse.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/modifyPassword")
    public ResponseEntity<String> modifyPassword(String checkcode, Emp emp) {

        //将数据存放到自定义类中
        VariableCode variableCode = new VariableCode();
        variableCode.setEmp(emp);
        variableCode.setCheckcode(checkcode);

        //将获取到的验证码存放到redis缓存中

        String redisCheckCode = (String)redisTemplate.opsForValue().get(variableCode.getEmp().getPhone());


        //将页面传入的验证码与redis中存放的验证码进行判断
        if(redisCheckCode == null || ! redisCheckCode.equals(variableCode.getCheckcode())){
            return new ResponseEntity<String>("验证码不存在", HttpStatus.INTERNAL_SERVER_ERROR);
       }

        //判断是否有当前Emp对象 进行修改密码操作
        if(variableCode.getEmp()!=null){
            int flag = userService.modifyPassword(variableCode.getEmp());
            // 1 代表更新成功  2 代表失败
            if(flag==1){
                return new ResponseEntity<String>("密码修改成功", HttpStatus.OK);
            }
        }

        //删除redis中存放的验证码
        redisTemplate.delete(variableCode.getCheckcode());

        return new ResponseEntity<String>("无此用户！", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/validatecode")
    public ResponseEntity<Void> validatecode(HttpServletResponse response) throws IOException {
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        String hash1 = Integer.toHexString(rdm.nextInt());
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String capstr = hash1.substring(0, 4);

        /*存入验证码*/
        session.setAttribute("checkcode", capstr);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(capstr, 8, 24);
        g.dispose();
        response.setContentType("image/jpeg");

        OutputStream strm = response.getOutputStream();
        ImageIO.write(image, "jpeg", strm);
        strm.close();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }





}
