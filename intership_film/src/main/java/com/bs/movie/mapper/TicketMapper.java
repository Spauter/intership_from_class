package com.bs.movie.mapper;

import com.bs.movie.entity.Ticket;
import com.bs.movie.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TicketMapper {
    @Insert("insert into sp_ticket values (#{tid},#{mname},#{uname},#{date},#{hname},#{starttime},#{prize},#{genre})")
    int insert(Ticket ticket);



    @Select("select * from sp_ticket")
    List<Ticket> selectAll();

    @Select("<script>" +
            "select * from sp_ticket" +
            "<where>" +
            "<if test='uname!=null'>and uname= #{uname} </if>" +
            "<if test='mname!=null'>and mname= #{mname} </if>" +
            "</where>" +
            "</script>")
    List<Map<String,Object>> selectTick(@Param("uname") String uname, @Param("mname") String mname);

    @Delete("delete from sp_ticket where tid=#{tid}")
    int delete(Integer tid);

}
