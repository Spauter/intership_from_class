package com.bs.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resorder")
public class ResOrder implements Serializable {
    @TableId(value = "roid",type = IdType.AUTO)
    private Integer roid;
    private Integer fid;
    private  Integer userid;
    private  String address;
    private String tel;
    private String ordertime;
    private String delivertime;
    private String ps;
    private Integer status;

}
