package com.czxy.fore.domain;

import com.czxy.dept.domain.Emp;

/**
 * Created by Administrator on 2018/10/27.
 */
public class VariableCode {

    private Emp emp;
    private String checkcode;

    public Emp getEmp() {
        return emp;
    }

    public void setEmp(Emp emp) {
        this.emp = emp;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
}
