package com.bs.web.mode;


import com.bs.bean.ResFood;
import lombok.Data;

import java.util.List;

@Data
public class MypageBean {
    Integer pageno;
    Integer pagesize;
    String sort;
    String sortby;
    List<ResFood> Dataset;
    Long total;
    Integer totalpages;
    Integer pre;
    Integer next;
}
