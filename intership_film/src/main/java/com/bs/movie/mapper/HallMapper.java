package com.bs.movie.mapper;

import com.bs.movie.entity.Hall;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

interface HallMapper {
    /**
     * 查所有影
     * @return
     */
    @Select("select * from sp_hall")
    List<Map<String,Object>> selectAll();


    /**
     * 添加影厅
     * 除非是装修扩建一般也不会动用他。
     * @param hall
     * @return
     */
    @Insert("insert into sp_hall values (default,#{hname},#{capacity})")
    int insert(Hall hall);



}
