package com.bs.movie.listener;



import com.bs.movie.util.ConstantInfo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * Company 源辰信息
 * 创建文件上传目录
 * @author navy
 * @date 2024/3/4
 * @Email haijunzhou@hnit.edu.cn
 */
@WebListener
public class CreateUploadPathListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getInitParameter("upload_path");
        if (path == null || path.isBlank()) { // 说明用户没有指定文件的保存路径
            path = "demo_fils";
        }

        // 获取服务器的绝对路径
        String contextPath = sce.getServletContext().getRealPath("/"); // 到当前项目下
        File parent = new File(contextPath).getParentFile(); // 此时这个File指向webapps目录
        File fl = new File(parent, path);
        if (!fl.exists()) {
            fl.mkdirs();
        }

        ConstantInfo.contextPath = contextPath;
        ConstantInfo.basepath = parent.getAbsolutePath();
        ConstantInfo.uploadpath = path;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
