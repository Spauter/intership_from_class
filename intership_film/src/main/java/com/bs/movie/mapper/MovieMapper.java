package com.bs.movie.mapper;

import com.bs.movie.entity.Movie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MovieMapper {
    /**
     * 插入电影
     * @param movie
     */
    @Insert("insert into sp_movie values (#{mid},#{mnane},#{director},#{genre},#{rate_num},#{runtime},#{pubdate},#{starts},#{votes},#{summary},#{image})")
    void insert(Movie movie);

    /**
     *
     * @return
     */
    @Select("select * from sp_movie")
    List<Map<String,Object>>selectAll();


    @Select("select * from sp_hall")
    List<Map<String,Object>>selectAll1();
    /**
     * 根据电影名称查询电影名，采用的模糊查询
     * @param mname
     * @return
     */
    @Select("<script>" +
            "select * from sp_movie" +
            "<where>" +
            "<if test='mname!=null'>and mname like #{mname}</if>" +
            "<if test='votes!=null'>and votes &gt;= #{votes}</if>" +
            "</where>" +
            "</script>")
    List<Map<String,Object>>selectByName(@Param("mname") String mname,@Param("votes") Integer votes);
    /**
     * 热门电影
     * 用于右侧排行
     *
     */
    @Select("select * from sp_movie order by votes desc limit 6")
    List<Map<String,Object>>hotMovie();

    @Select("select * from sp_movie where mid=250")
    List<Map<String, Object>> selectByMid();



    @Select("<script>" +
            "select * from sp_movie" +
            "<where>" +
            "<if test='begin!=null'>and opendate &gt;=#{begin}</if>" +
            "<if test='begin!=null'>and opendate &lt;=#{end}</if>" +
            "</where>" +
            "</script>")
    List<Movie> select(@Param("begin") String begin,
                         @Param("end") String end);

}
