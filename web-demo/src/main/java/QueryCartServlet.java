import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
@WebListener("/querycart.s")
public class QueryCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        // 获取购物车列表
        List<Map<String, Object>> cartList = (List<Map<String, Object>>) session.getAttribute("cartList");

        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 返回页面
        out.println("<html>");
        out.println("<head><title>购物车</title></head>");
        out.println("<body>");

        if (cartList == null || cartList.isEmpty()) {
            out.println("<h1>购物车为空</h1>");
        } else {
            out.println("<table border=\"1\">");
            out.println("<tr><th>商品名称</th><th>价格</th></tr>");
            for (Map<String, Object> item : cartList) {
                out.println("<tr>");
                out.println("<td>" + item.get("itemName") + "</td>");
                out.println("<td>" + item.get("price") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("</body></html>");
    }
}