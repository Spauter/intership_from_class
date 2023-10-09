package com.yc.test;

import com.yc.entity.Film;
import com.yc.entity.User;
import com.yc.mapper.FilmMapper;
import com.yc.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class FilmTest {
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
    public void testAll() {
        try (SqlSession session = gerFactory().openSession()) {
            FilmMapper mapper = session.getMapper(FilmMapper.class);
            List<Film> list = mapper.findAll();
            for (Film u : list) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFinds() {
        try (SqlSession session = gerFactory().openSession()) {
            FilmMapper mapper = session.getMapper(FilmMapper.class);
            List<Film> list = mapper.finds();
            for (Film u : list) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFinds1() {
        try (SqlSession session = gerFactory().openSession()) {
            FilmMapper mapper = session.getMapper(FilmMapper.class);
            List<Film> list = mapper.finds1();
            for (Film u : list) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFindByDis() {
        try (SqlSession session = gerFactory().openSession()) {
            session.getMapper(FilmMapper.class);
            System.out.println(session);
            FilmMapper filmMapper = session.getMapper(FilmMapper.class);
            System.out.println(filmMapper);
            List<Film> list = filmMapper.findByDis();
            for (Film u : list) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindDis() {
        try (SqlSession session = gerFactory().openSession()) {
            session.getMapper(FilmMapper.class);
            FilmMapper filmMapper = session.getMapper(FilmMapper.class);
            Film film = filmMapper.findByFidStep(1);
            System.out.println("film = " + film);
            System.out.println(film.getFname() + "\n" + film.getFilmType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
