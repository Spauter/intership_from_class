package com.bs.biz;


import com.bs.bean.ResAdmin;
import org.springframework.web.bind.annotation.RequestParam;

public interface ResAdminiBiz  {
    ResAdmin findByIDAndPwd(@RequestParam String uname,@RequestParam String pwd);
}
