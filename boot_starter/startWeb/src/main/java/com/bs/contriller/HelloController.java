package com.bs.contriller;

import com.bs.dateFormatAutoConfiguration.demo.BsTimeFunction;
import com.bs.jdbcTemplate.config.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    private BsTimeFunction bsTimeFunction;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
    public String home(@RequestParam String name){return bsTimeFunction.showtime(name);}

    @RequestMapping("/all")
    public List all(){return jdbcTemplate.select("select * from sp_movie");}

}
