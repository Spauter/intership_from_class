package com.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.bean.ResUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface ResUserMapper extends BaseMapper<ResUser> {

}
