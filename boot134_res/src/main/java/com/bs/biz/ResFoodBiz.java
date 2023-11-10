package com.bs.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.bean.ResFood;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ResFoodBiz {
    List<ResFood>findAll();
    ResFood findById(Integer fid);
    Page<ResFood> findByPage(int pageno,int pagesize,String sortBy,String sort);

    int AddFood(ResFood resFood);







}
