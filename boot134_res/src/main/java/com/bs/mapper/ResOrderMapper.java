package com.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.bean.ResOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResOrderMapper extends BaseMapper<ResOrder> {
    @Insert("insert into resorderitem values (default,#{roid},#{fid},#{dealprice},#{num})")
    int insertOrder(ResOrder order);
}
