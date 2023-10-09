package com.yc.mapper;

import com.yc.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    List<User> getByRoleAndStatEntity(User user);
    List<User> findByRoleAndStatus(@Param("u-r")String role,@Param("st") Integer status);
    List<User> findByRoleAndStatusMap(Map<String,Object>map);
    List<User> getByRoleAndStatus(String role,Integer status);

    List<User>findAll();
    Map<String,Object>find(Integer id);


    User findById(Integer id);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    int add(User user);
}
