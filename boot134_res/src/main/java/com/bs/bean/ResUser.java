package com.bs.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("resuser")
public class ResUser {
    @TableId(value = "userid",type = IdType.AUTO)
    private Integer userid;
    private String username;
    private String pwd;
    private String email;

    @TableField(exist = false)
    private String yzm;

}
