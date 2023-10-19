package com.bs.contriller;

import com.bs.dateFormatAutoConfiguration.demo.BsTimeFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private BsTimeFunction bsTimeFunction;

    @RequestMapping("/")
    public String home(@RequestParam String name){return bsTimeFunction.showtime(name);}

}
