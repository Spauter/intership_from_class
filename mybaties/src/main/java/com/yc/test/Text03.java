package com.yc.test;

import com.yc.entity.User;
import com.yc.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;



import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Text03 {
    public SqlSessionFactory gerFactory(){
        String res= "mybatis-config.xml";
        try{
            return  new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(res));

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public  void  testAll(){
        try (SqlSession session=gerFactory().openSession()){
            UserMapper mapper=session.getMapper(UserMapper.class);
            List<User>list=mapper.findAll();
            for (User u:list){
                System.out.println(u);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testFind(){
        try (SqlSession session=gerFactory().openSession()){
            UserMapper mapper=session.getMapper(UserMapper.class);
            Map<String,Object>map=mapper.find(2);
            System.out.println(map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public  void getFindByRolrAndStatus(){
      try (SqlSession session=gerFactory().openSession()){
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> list = mapper.findByRoleAndStatus("叼毛", 1);
        for (User u : list) {
            System.out.println(u);
        }
    }catch(Exception e) {
          e.printStackTrace();
      }
    }

    @Test
    public void getFindByRoleAndSatusEntity(){
        try (SqlSession session=gerFactory().openSession()){
            UserMapper mapper=session.getMapper(UserMapper.class);
            Map<String,Object>map=new HashMap<>();
            map.put("u_role","叼毛");
            map.put("status",1);
            List<User>list =mapper.findByRoleAndStatusMap(map);
            for(User u:list){
                System.out.println(u);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getFindByRoleAndStatusEntity(){
        try (SqlSession session=gerFactory().openSession()){
            UserMapper mapper=session.getMapper(UserMapper.class);
            User user=new
                    User();
            user.setStatus(1);
            user.setU_role("叼毛");
            List<User>list=mapper.getByRoleAndStatEntity(user);
            for(User u:list){
                System.out.println(u);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
