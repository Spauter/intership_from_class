package com.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.bean.ResFood;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ResFoodMapper extends BaseMapper<ResFood> {
}
