package com.bs.web.mode;

import com.bs.bean.ResFood;

public class Cartltem {
    private ResFood food;
    private Integer num;

    private Double smallCount;

    public ResFood getFood() {
        return food;
    }

    public void setFood(ResFood food) {
        this.food = food;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getSmallCount() {
        return smallCount;
    }

    public void setSmallCount(Double smallCount) {
        this.smallCount = this.food.getRealprice()*this.num;
    }
}
