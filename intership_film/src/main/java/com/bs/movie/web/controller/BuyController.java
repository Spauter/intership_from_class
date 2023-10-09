package com.bs.movie.web.controller;

import com.bs.movie.entity.Plan;
import com.bs.movie.entity.Ticket;
import com.bs.movie.entity.User;
import com.bs.movie.mapper.TicketMapper;
import com.bs.movie.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 关于买票的操作
 * 包括基本购买，修改和退票
 * 管理只能查看
 */
@WebServlet(value = "/buy/*")

public class BuyController extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        resp.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String op=req.getParameter("op");
        if ("add".equals(op)){
            addTicket(req,resp);
        }
    }

    MainPageController ma = new MainPageController();

    /**
     * 购买电影票，实现的是插入操作
     * @param request
     * @param response
     */
    public void addTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try(SqlSession session= MybatisUtil.getSession()) {
            TicketMapper mapper = session.getMapper(TicketMapper.class);
            List<Plan> planList = (List<Plan>) request.getSession().getAttribute("planList");
            User user = (User) request.getSession().getAttribute("userinfo");
            String date = planList.get(0).getDate();
            System.out.println(date);
            String hname = planList.get(0).getHname();
            System.out.println(hname);
            String mname = planList.get(0).getMname();
            System.out.println(mname);
            Float prize = planList.get(0).getPrize();
            System.out.println(prize);
            String starttime = planList.get(0).getStarttime();
            System.out.println(starttime);
            System.out.println(user);
            String uname = user.getUname();
            System.out.println(uname);
            Ticket ticket = new Ticket(null,mname,uname,starttime,hname,date,prize,null);
            System.out.println(ticket.toString());
            mapper.insert(ticket);
            session.commit();
            send(response,ticket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
