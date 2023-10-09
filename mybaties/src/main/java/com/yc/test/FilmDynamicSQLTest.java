package com.yc.test;

import com.yc.entity.Film;
import com.yc.entity.FilmType;
import com.yc.mapper.FilmMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilmDynamicSQLTest {

    @Test
    public void testFindByTermIf() {
        try (SqlSession session = getFactory().openSession()) {

            FilmMapperDynamicSQL mapper = session.getMapper(FilmMapperDynamicSQL.class);

            List<Integer> fids = Arrays.asList(1, 2, 3);
            Film film=new Film();
            film.setDirector("%郭%");
            List<Film> list = mapper.findByTermIf(film);
            for (Film f : list) {
                System.out.println(f);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Test
    public void testFindByTermTrim() {
        try (SqlSession session = getFactory().openSession()) {
            //  List<Film> list = session.selectList("com.yc.mapper.FilmMapperS");
            FilmMapperDynamicSQL mapper = session.getMapper(FilmMapperDynamicSQL.class);
            Film film = new Film();
            film.setActor("%吴%");
            List<Film> list = mapper.findByTermTrim(film);
            for (Film u : list) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Test
    public void testFindByTermChoose() {
        try (SqlSession session = getFactory().openSession()) {
            //  List<Film> list = session.selectList("com.yc.mapper.FilmMapperS");
            FilmMapperDynamicSQL mapper = session.getMapper(FilmMapperDynamicSQL.class);
            Film film = new Film();
            film.setFid(3);
            FilmType filmType = new FilmType();
            filmType.setTid(1);
            film.setFilmType(filmType);
             film.setActor("%吴%");
            List<Film> list = mapper.findByTermChoose(film);
            for (Film u : list) {
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    //    @Test
//    public void testAddFilms() {
//        try (SqlSession session = getFactory().openSession()) {
//
//            FilmMapperDynamicSQL mapper = session.getMapper(FilmMapperDynamicSQL.class);
//            //Film实体类去生成一个无参和一个带所有参数构造方法
//            List<Film> list = new ArrayList<>();
//            FilmType filmType = new FilmType();
//            filmType.setTid(2);
//            list.add(new Film("电影3", "演员2", "导演2", 78.7f, "film_pics/1.jpg", filmType));
//            list.add(new Film("电影4", "演员4", "导演4", 48.7f, "film_pics/1.jpg", filmType));
//            mapper.addFilms(list);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//    }
//
//
//    @Test
//    public void testFindByFids() {
//        try (SqlSession session = getFactory().openSession()) {
//
//            FilmMapperDynamicSQL mapper = session.getMapper(FilmMapperDynamicSQL.class);
//
//            List<Integer> fids = Arrays.asList(1, 2, 3);
//            List<Film> list = mapper.findByFids(fids);
//            for (Film f : list) {
//                System.out.println(f);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//    }
//
//    @Test
//    public void testUpdateFilm() {
//        try (SqlSession session = getFactory().openSession()) {
//
//            FilmMapperDynamicSQL mapper = session.getMapper(FilmMapperDynamicSQL.class);
//            Film film = new Film();
//            film.setFid(2);
//            film.setFname("封神");
//            film.setActor("费翔、李雪健、黄渤、娜然");
//            //film.setDirector("乌尔善");
//            //sfilm.setFpic("film_pics/2.jpg");
//
//
//            mapper.updateFilm(film);
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//    }
//

//
//
//
//    @Test
//    public void testFindByTremIf() {
//        try (SqlSession session = getFactory().openSession()) {
//            //  List<Film> list = session.selectList("com.yc.mapper.FilmMapperS");
//            FilmMapperDynamicSQL mapper = session.getMapper(FilmMapperDynamicSQL.class);
//            Film film = new Film();
//            film.setActor("%1%");
//            List<Film> list = mapper.findByTremIf(film);
//            for (Film u : list) {
//                System.out.println(u);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//    }
//
//
    public SqlSessionFactory getFactory() {
        String res = "mybatis-config.xml";

        try {
            return new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
