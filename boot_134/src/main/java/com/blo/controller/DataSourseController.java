package com.blo.controller;


import com.blo.MyConfig;
import com.blo.bean.Apple;
import com.blo.bean.Banana;
import com.blo.bean.Pear;
import com.blo.bean.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

@RestController
@Log4j2
@Import({Apple.class, Pear.class, Banana.class})
public class DataSourseController implements ApplicationContextAware {

    private final DataSource dataSource;

    @Autowired
    private MyConfig myConfig;

    @Autowired
    private Product product;
    private ApplicationContext ac;

    public DataSourseController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("ds")
    public String getDsInfo()throws SQLException{
        String[] bnams=this.ac.getBeanDefinitionNames();
        for(String b:bnams){
            System.out.println(b);
        }
        log.info(product.toString());
        log.info(myConfig);
        log.debug("debug调试");
        log.info("info信息");
        log.warn("warn警告");
        log.error("error错误");
        log.fatal("fatal致命");
        if (dataSource == null) {
            return "啥也没有";
        } else {
            return dataSource.toString();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac=applicationContext;
    }
}
