package com.bs.movie.mapper;

import com.bs.movie.entity.Plan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PlanMapper {
    /**
     * 查询排片表，默认按演出时间排序
     *
     */
    @Select("<script>" +
            "select * from sp_plan" +
            "<where>" +
            "<if test='mname!=null'>and mname=#{mname}</if>" +
            "</where>" +
            "</script>")
    void  selectAll();


    @Select("select * from sp_plan")
    List<Plan> select();


    /**
     * 根据电影名字和时间筛选电影，
     * 一般用于用户查询、
     * 两个可以为空
     * @param mname
     * @param starttime
     * @return
     */
//    @Select("<script>" +
//            "select pid,a.hname,capacity,mname,date,starttime,prize from sp_hall a join sp_plan b where a.hname=b.hname" +
//            "<where>" +
//            "<if test='b.hname!=null'>and b.hname=#{b.hname}</if>" +
//            "<if test='starttime!=null'>and starttime &gt; #{starttime}</if>" +
//            "</where>" +
//            "</script>")
//    List<Map<String,Object>> lookUpPlan(String mname,String starttime);

    /**
     * 安排电影播放场次，时间
     * 定价
     * 仅限管理员操作
     * @param plan
     */
//    @Insert("insert into sp_plan values (default,#{mname},#{hname,#{starttime},#{date},#{prize})")
//    void insert(Plan plan);

}
