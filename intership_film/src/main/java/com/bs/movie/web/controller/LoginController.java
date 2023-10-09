package com.bs.movie.web.controller;


import com.bs.movie.entity.User;
import com.bs.movie.enums.ResultEnum;
import com.bs.movie.mapper.UserMapper;
import com.bs.movie.util.MybatisUtil;
import com.bs.movie.vo.ResultVO;
import com.ly.common.bean.Result;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用于登录左右
 */
@WebServlet(name="LoginController",value = "/login/*")
public class LoginController extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        super.doGet(req, resp);

        String op=request.getParameter("op");
        if("login".equals(op)){
            login(response,request);
        }else if("regin".equals(op)){
            regin(response,request);
        }else if ("userinfo".equals(op)){
            userinfo(request,response);
        }
    }

    /**
     * 筛选登录用户，如果返回为空，说明用户名或者密码错误
     * 也或者是用户不存在
     *
     * @param response
     * @param request
     * @return
     */
    private void login(HttpServletResponse response, HttpServletRequest request) throws IOException {
        try (SqlSession session = MybatisUtil.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            String email=request.getParameter("email");
            String pwd=request.getParameter("password");
            User user=mapper.login(email,pwd);
            System.out.println(user);
            if(user==null)
            {
                send(response, new ResultVO(ResultEnum.DATA_NULL));
            }else{
                request.getSession().setAttribute("userinfo",user);
                send(response,user);
            }
        }
    }

    private void userinfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try(SqlSession session = MybatisUtil.getSession()) {
            User userinfo = (User) request.getSession().getAttribute("userinfo");
            String uname = userinfo.getUname();
            if (userinfo == null){
                send(response, new ResultVO(ResultEnum.DATA_NULL));
            }else {
                send(response, new ResultVO(ResultEnum.SUCCESS, userinfo));
            }
        }

    }

    private void loyout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.getSession().invalidate();
        response.sendRedirect("index.html");
    }
    /**
     * 注册部分，就是插入操作
     * 由于主键是uid，一旦有相同的会添加失败
     * 默认status为null
     * @param response
     * @param request
     * @return
     */
    public void regin(HttpServletResponse response , HttpServletRequest request) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try(SqlSession session=MybatisUtil.getSession()){
            UserMapper mapper=session.getMapper(UserMapper.class);
            String uid = request.getParameter("uid");
            String email=request.getParameter("email");
            //String uname=request.getParameter("uname");
            String uname = request.getParameter("uname");
            String pwd=request.getParameter("pwd");
            String status = request.getParameter("status");
            User user=new User(uid,uname,email,pwd,status);
            request.getSession().setAttribute("user", user);
            System.out.println();
            int i= mapper.insert(user);
            session.commit();
            if(i!=1){
                send(response,"注册失败");
            }else {
                send(response,user);
            }
        }

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getSession().getAttribute("userinfo") ==null){
            resp.sendRedirect("login.html");
        }
        chain.doFilter(request,response);
    }

    /**
     * 修改密码部分
     * 先验证uid在不在
     * 再验证密码是否与原密码相同
     * @param request
     * @param response
     * @return
     */
    public void modifyPwd(HttpServletRequest request, HttpServletResponse response){
        try(SqlSession session=MybatisUtil.getSession()) {
            Result result;
            User user;
            UserMapper mapper = session.getMapper(UserMapper.class);
            String email = request.getParameter("email");
            user = mapper.selectByEmail(email);
            if (user == null) {
                result = new Result(0, "您输入的账号不存在");
            } else {
                String pwd = user.getPwd();
                String npwd = request.getParameter("pwd");
                result = pwd == npwd ? (new Result(0, "修改的密码与原密码相同")) : (new Result(200, "密码修改成功"));
            }
            send(response,result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void selectAll(){
        SqlSession session=MybatisUtil.getSession();
        UserMapper mapper=session.getMapper(UserMapper.class);
        List<Map<String,Object>>list=mapper.selectAll();
        for (Map<String ,Object> m:list){
            m.toString();
        }

    }

}
