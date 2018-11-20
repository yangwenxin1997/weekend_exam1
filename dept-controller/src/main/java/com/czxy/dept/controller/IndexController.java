package com.czxy.dept.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/10/22.
 */
@Controller
public class IndexController {

    @RequestMapping("/{path}")
    public String index(@PathVariable("path") String path){
        return path;        //例如：index.html页面
    }

    @RequestMapping("/pages/{dir}/{path}")
    public String home2(@PathVariable("dir") String d ,@PathVariable("path") String p){
        return "/pages/" + d + "/" + p;
    }

}
