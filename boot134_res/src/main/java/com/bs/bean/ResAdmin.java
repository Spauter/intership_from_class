package com.bs.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resadmin")
public class ResAdmin implements Serializable {
    @TableId(value = "raid",type = IdType.AUTO)
    private Integer raid;
    private String ranema;
    private String rapwd;
    //对于 private boolean isXXX MyBatisPlus 会转成 XXXX 需要 @TableField("isXXX") 指定
    //如果表属性是关键字比如order,则需要如下注释 @TableField("'order'")
    //对于数据库表中没有的字段,需要@TableField(exist=false)指定
}
