package com.bs.contriller;

import com.bs.dateFormatAutoConfiguration.demo.BsTimeFunction;
import com.bs.jdbcTemplate.config.JdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class HelloController {
    @Autowired
    private BsTimeFunction bsTimeFunction;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
    public String home(@RequestParam String name){return bsTimeFunction.showtime(name);}

    @RequestMapping("/all")

    public List<Map<String,Object>> all(){
        log.info("as");
        return jdbcTemplate.select("select * from sp_movie");}

}
