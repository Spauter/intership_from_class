package com.yc.demo.作业;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class AddCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        // 获取商品信息
        String itemName = request.getParameter("itemName");
        double price = Double.parseDouble(request.getParameter("price"));

        // 获取购物车列表
        List<Map<String, Object>> cartList = (List<Map<String, Object>>) session.getAttribute("cartList");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        // 将商品信息存入map集合
        Map<String, Object> item = new HashMap<>();
        item.put("itemName", itemName);
        item.put("price", price);

        // 将商品信息存入list集合
        cartList.add(item);

        // 将list集合存入会话
        session.setAttribute("cartList", cartList);
    }
}
