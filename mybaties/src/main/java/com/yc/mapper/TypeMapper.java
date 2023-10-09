package com.yc.mapper;

import com.yc.entity.FilmType;


public interface TypeMapper {
    FilmType findByTid(Integer tid);

    FilmType findByTidStep(Integer tid);

    /**
     * 根据类型编号查看信息,可以保护电影信息
     * 多表查询，根据类型编号查看类型信息以及类型拥有的电影信息
     * @param tid
     * @return
     */
    FilmType findByTidPlus(Integer tid);

}
