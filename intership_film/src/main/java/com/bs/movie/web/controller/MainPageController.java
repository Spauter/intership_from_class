package com.bs.movie.web.controller;


import com.bs.movie.entity.Movie;
import com.bs.movie.entity.Plan;
import com.bs.movie.entity.Ticket;
import com.bs.movie.mapper.MovieMapper;
import com.bs.movie.mapper.PlanMapper;
import com.bs.movie.mapper.TicketMapper;
import com.bs.movie.util.MybatisUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于主界面渲染的部分
 */
@WebServlet(name="MainOAgeController",value ="/movie/*" )
public class MainPageController extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        resp.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String op=req.getParameter("op");
//        showHotMovie(resp,req);
        if("query".equals(op)){
            showMainPage(resp,req);
        }else if("search".equals(op)){
            search(resp,req);
        }else if("top6".equals(op)){
            /*
             * 用于在浏览器端测试热映电影，不会执行此功能
             */
            showHotMovie(resp,req);
        }else if ("query1".equals(op)){
            query(req, resp);
        }else if ("hall".equals(op)){
            Hall(resp,req);
        }else if ("movie".equals(op)){
            Movie(resp,req);
        }else if ("plan".equals(op)){
            Plan(resp,req);
        } else if ("order".equals(op)){
            Order(resp,req);
        }


    }

    /**
     * 用于渲染电影，当🔍没有输入任何数据时显示所有结果
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    public void showMainPage(HttpServletResponse response, HttpServletRequest request)throws IOException{
        try(SqlSession session= MybatisUtil.getSession()){
            final MovieMapper mapper=session.getMapper(MovieMapper.class);
            List<Map<String,Object>>list=new ArrayList<>();
            list=mapper.selectAll();
            send(response,list);
        }
    }
    public void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try (SqlSession session = MybatisUtil.getSession()){
            final MovieMapper mapper = session.getMapper(MovieMapper.class);
            String begin = request.getParameter("begin");
            String end = request.getParameter("end");
            String page = request.getParameter("page");
            String size = request.getParameter("size");
            page = page == null ? "1": page;
            size = size == null ? "8": size;
            final Page<Object> pages = PageHelper.startPage(Integer.parseInt(page),
                    Integer.parseInt(size));
            List<Movie> list = mapper.select(begin, end);
            final long total = pages.getTotal();
            Map<String, Object> ret = new HashMap<>();
            ret.put("rows", list);
            ret.put("total", total);
            send(response, ret);
        }
    }


    /**
     * 按名字查询电影，采用模糊查询
     * 如何没有输入，则返回空
     * @param response
     * @param request
     * @return
     */
    public void search(HttpServletResponse response,HttpServletRequest request) throws IOException {
        try (SqlSession session=MybatisUtil.getSession()){
            final  MovieMapper mapper=session.getMapper(MovieMapper.class);
            String mname=request.getParameter("mname");
//            System.out.printf(mname==null?true:false;
            String votes=request.getParameter("votes");
            if(null!=mname){
                mname="%"+mname+"%";
            }
            List<Map<String,Object>>list=new ArrayList<>();
            list = mapper.selectByName(mname, votes!=null?Integer.parseInt(votes):null);
            send(response,list);
        } catch (IOException e) {
            send(response,e);
            throw new RuntimeException(e);

        }
    }

    /**
     * 显示右侧的排行版，根据votes的高低排序，显示6个
     * 返回的是List<String>电影名字
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    public void showHotMovie(HttpServletResponse response, HttpServletRequest request)throws IOException{
        try(SqlSession session= MybatisUtil.getSession()) {
            final MovieMapper mapper = session.getMapper(MovieMapper.class);
            List<Map<String, Object>> list = mapper.hotMovie();
            List<String> ret = new ArrayList<>();
            send(response,list);
        }
    }



    /**
     * 测试用，请放心忽略
     */
    @Test
    public void se(){
        SqlSession session=MybatisUtil.getSession();
        MovieMapper mapper=session.getMapper(MovieMapper.class);
        List<Map<String,Object>>list=new ArrayList<>();
        list=mapper.hotMovie();
        for(Map<String,Object> m:list){
            System.out.println(m);
        }
    }

    public void Hall(HttpServletResponse response, HttpServletRequest request)throws IOException{
        try(SqlSession session= MybatisUtil.getSession()){
            final MovieMapper mapper=session.getMapper(MovieMapper.class);
            List<Map<String,Object>>list=new ArrayList<>();
            list=mapper.selectAll1();
            send(response,list);
        }
    }

    public void Movie(HttpServletResponse response, HttpServletRequest request)throws IOException{
        try(SqlSession session= MybatisUtil.getSession()){
            final MovieMapper mapper=session.getMapper(MovieMapper.class);
            List<Map<String,Object>>list=new ArrayList<>();
            list=mapper.selectByMid();
            send(response,list);
        }
    }

    public void Plan(HttpServletResponse response, HttpServletRequest request)throws IOException{
        try(SqlSession session= MybatisUtil.getSession()){
            final PlanMapper mapper=session.getMapper(PlanMapper.class);
            List<Plan> list=new ArrayList<>();
            list=mapper.select();
            System.out.println(list);
            request.getSession().setAttribute("planList", list);
            send(response,list);
        }
    }

    public void Order(HttpServletResponse response, HttpServletRequest request)throws IOException{
        try(SqlSession session= MybatisUtil.getSession()){
            final TicketMapper mapper=session.getMapper(TicketMapper.class);
            List<Ticket> list=new ArrayList<>();
            list=mapper.selectAll();
            send(response,list);
            System.out.println(list);
        }
    }

}
