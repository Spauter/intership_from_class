package com.bs.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private  Integer userid;
    private  String address;
    private String tel;
    private Date ordertime;
    private Date delivertime;
    private String ps;
    private String status;

}
