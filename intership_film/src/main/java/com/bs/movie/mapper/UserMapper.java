package com.bs.movie.mapper;

import com.bs.movie.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface  UserMapper {
    /**
     * 用于登录
     * @param email
     * @param pwd
     * @return
     */
    @Select("select * from sp_user where email=#{email} and pwd=#{pwd}")
    User login(@Param("email") String email,@Param("pwd") String pwd);


    /**
     * 用于注册用户
     */
    @Insert("insert into sp_user(uid,email,uname,pwd,status) values (#{uid},#{email},#{uname},#{pwd},#{status})")
    int insert(User user);


    /**
     * 用于修改用户密码
     * 需要输入email验证
     * @param pwd
     * @param email
     */
    @Update("update sp_user set pwd=#{pwd} where email=#{email}")
    void modify(String pwd,String email);

    /**
     * 根据email查询用户信息
     * 按普遍原理来说，结果只有一条或者null
     * @param email
     * @return
     */
    @Select("select * from sp_user where email=#{email}")
    User selectByEmail(String email);




    @Select("select * from sp_user")
    List<Map<String,Object>> selectAll();
}
