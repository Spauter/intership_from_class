package com.yc.test;

import com.yc.entity.FilmType;
import com.yc.mapper.TypeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FilmTypeTest {
    public SqlSessionFactory gerFactory() {
        String res = "mybatis-config.xml";
        try {
            return new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(res));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testFind() {
        try (SqlSession session = gerFactory().openSession()) {
            TypeMapper mapper = session.getMapper(TypeMapper.class);
            System.out.println(mapper.findByTid(1));
            System.out.println(mapper.findByTid(2).getTname());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByTidPlus() {
        try (SqlSession session = gerFactory().openSession()) {
            TypeMapper mapper = session.getMapper(TypeMapper.class);
            FilmType filmType = mapper.findByTidPlus(5);
            System.out.println(filmType.getFilmList());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
        public void testFindByTidStep() {
        try (SqlSession session = gerFactory().openSession()) {
            TypeMapper mapper = session.getMapper(TypeMapper.class);
            FilmType filmType = mapper.findByTidStep(1);
            System.out.println(filmType.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
