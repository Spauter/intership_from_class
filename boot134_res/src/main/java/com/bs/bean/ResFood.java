package com.bs.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor@Data
@TableName("resfood")
public class ResFood implements Serializable {
    @TableId(value = "fid",type = IdType.AUTO)
    private Integer fid;
    private String fname;
    @TableField(value = "normprice")
    private double mormprice;
    private double realprice;
    private String detail;
    private String fphoto;
}
