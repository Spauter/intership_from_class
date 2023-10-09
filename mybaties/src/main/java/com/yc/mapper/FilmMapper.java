package com.yc.mapper;

import com.yc.entity.Film;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface  FilmMapper {
    List<Film>findAll();

    /**多表查询
     * 需要包含一个FilmTypr
     * @return
     */
    List<Film>finds();


    List<Film>finds1();

    List<Film>findByDis();

    /**
     * 根据电影编号查看电影信息
     * 分步
     * @param id
     * @return
     */
    Film findByFidStep( @Param("fid") Integer id);




}
