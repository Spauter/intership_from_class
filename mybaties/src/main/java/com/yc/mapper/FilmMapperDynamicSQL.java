package com.yc.mapper;

import com.yc.entity.Film;

import java.util.List;

/**
 * 动态sql
 */
public interface FilmMapperDynamicSQL {
    /**
     * 多条件查询之选择一个
     * @param film
     * @return
     */
    List<Film> findByTermChoose(Film film);


    /**
     * 多个条件只选择一个运行
     * @param film
     * @return
     */
    List<Film> findByTermTrim(Film film);


    /**
     * 多条件查询
     * @param film
     * @return
     */
    List<Film> findByTermIf(Film film);
//
//    public void addFilms(List<Film> films);
//
//    public List<Film> findByFids(List<Integer> id);
//

    /**
     * 修改电影信息
     * @param film
     */
    void  updateFilm(Film film);


}
