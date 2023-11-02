package com.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.bean.ResFood;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ResFoodMapper extends BaseMapper<ResFood> {
    @Select("select * from resfood")
    List<ResFood> selectAll();

    @Select("<script>"
            +"select * from resfood"
                +"<where>"
                    +"<if test='fid !=null'>and fid =#{fid}</if>" +
                    "<if test='fname !=null'>and fname =#{fname}</if>"
                +"</where>"
            +"</script>")
    ResFood selectByFidOrName(Integer fid,String fname);



}
