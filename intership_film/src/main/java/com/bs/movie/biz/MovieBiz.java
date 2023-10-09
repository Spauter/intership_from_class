package com.bs.movie.biz;


import com.bs.movie.entity.Movie;
import com.bs.movie.mapper.MovieMapper;
import com.bs.movie.util.MybatisUtil;
import com.bs.movie.util.ReadExcelUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieBiz {
    List<String> list = new ArrayList<>();
    public void save(String path) throws IOException {
        File file = new File(path);
        try {
            if (path.endsWith("xlsx")) {
                list = ReadExcelUtil.readXlsx(file);
            } else if (path.endsWith("xls")) {
                list = ReadExcelUtil.readXls(file);
            } else {
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try(SqlSession session=MybatisUtil.getSession(false)) {
            MovieMapper mapper=session.getMapper(MovieMapper.class);
            int i=0;
            for(String s:list){
                String[] infos=s.split("=+");
                Movie movie=new Movie(i,infos[1],infos[2],infos[3],Float.parseFloat(infos[4]),(int)(Float.parseFloat(infos[5])),infos[6],infos[7],(int)Float.parseFloat(infos[8]),infos[9],infos[10]);
                mapper.insert(movie);
                i++;
            }
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        new MovieBiz().save("D:\\spouter\\JAVA\\IDEA\\intership_film\\src\\main\\resources\\1.xlsx");
    }


}


