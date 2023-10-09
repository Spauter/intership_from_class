package com.yc.entity;

public class User {
    private  Integer uid;
    private  String uname;
    private  String upwd;
    private  Integer status;
    private  String u_role;

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upwd='" + upwd + '\'' +
                ", status=" + status +
                ", u_role='" + u_role + '\'' +
                '}';
    }

    public void setU_role(String u_role) {
        this.u_role = u_role;
    }

    public String getU_role() {
        return u_role;
    }

    public Integer getUid() {
        return uid;
    }

    public String getuname() {
        return uname;
    }

    public String getUpwd() {
        return upwd;
    }


    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setuname(String uname) {
        this.uname = uname;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

}
