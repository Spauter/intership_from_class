package com.bs.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("resorderitem")
public class ResOrderItem {
    @TableId(value = "roiid",type = IdType.AUTO)
    private Integer roiid;
    private Integer roid;
    private Integer fid;
    private double dealprice;
    private Integer num;

}
