import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebListener("/addcart.s")
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
