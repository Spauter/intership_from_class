package com.bs.movie.web.controller;

import com.bs.movie.entity.Ticket;
import com.bs.movie.entity.User;
import com.bs.movie.mapper.TicketMapper;
import com.bs.movie.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    负责对订单执行操作，包括查看和删除
 */
@WebServlet(name = "OrderController",value = "/order/*")
public class OrderController extends BaseServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        resp.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String op = req.getParameter("op");
        if("search".equals(op)){
            selectTicket(req,resp);
        } else if ("drop".equals(op)) {
            delete(req,resp);
        }
    }

    /**
     *
     * 添加订单
     * @param request
     * @param response
     */


    /**
     * 查看订单,根据用户名查询订单
     * @param request
     * @param response
     * @throws IOException
     */
    public void selectTicket(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try(SqlSession session= MybatisUtil.getSession()) {
            TicketMapper mapper = session.getMapper(TicketMapper.class);
            User user = (User) request.getSession().getAttribute("userinfo");
            String uname = user.getUname();
            System.out.println(uname);
            String mname = request.getParameter("mname");
            List<Map<String,Object>> list=mapper.selectTick(uname,mname);
            if(list.size()==0){
                send(response,null);
            }else {
                send(response, list);
            }
        }
    }

    public void delete(HttpServletRequest request,HttpServletResponse response)throws IOException{
        try(SqlSession session=MybatisUtil.getSession()){
            TicketMapper mapper=session.getMapper(TicketMapper.class);

            String tid=request.getParameter("tid");
            if(tid==null){
                send(response,"无效操作");
                return;
            }
            int i=mapper.delete(Integer.parseInt(tid));
            session.commit();
            if(i==0){
                send(response,"删除失败");
            }else {
                send(response,"删除成功");
            }
        }
    }

    @Test
    public void test(){
        try(SqlSession session= MybatisUtil.getSession()) {
            TicketMapper mapper = session.getMapper(TicketMapper.class);
            List<Map<String, Object>> list = mapper.selectTick(null, null);

        }
    }
}
