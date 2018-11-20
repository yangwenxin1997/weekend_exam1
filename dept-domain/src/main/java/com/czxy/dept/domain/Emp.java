package com.czxy.dept.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * @author likk
 * @create 2018/10/25
 */
@Table(name = "t_emp")
public class Emp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ename;
    private String password;
    private String phone;
    private String mail;
    private String hobby;
    private String deptId;
    @Transient
    private Dept dept;

    public Emp() {
    }

    public Emp(String ename, String password, String phone, String mail, String hobby, String deptId, Dept dept) {
        this.ename = ename;
        this.password = password;
        this.phone = phone;
        this.mail = mail;
        this.hobby = hobby;
        this.deptId = deptId;
        this.dept = dept;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", ename='" + ename + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", hobby='" + hobby + '\'' +
                ", deptId='" + deptId + '\'' +
                ", dept=" + dept +
                '}';
    }
}
