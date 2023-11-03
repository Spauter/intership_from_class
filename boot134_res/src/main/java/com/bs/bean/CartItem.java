package com.bs.bean;


import java.io.Serializable;

public class CartItem implements Serializable {
    private ResFood resFood;
    private Integer num;
    private Double smallCount;
    public Double getSmallCount(){
        if(resFood!=null){
            smallCount=resFood.getRealprice()*num;
        }
        return smallCount;
    }

    public ResFood getResFood() {
        return resFood;
    }

    public Integer getNum() {
        return num;
    }

    public void setResFood(ResFood resFood) {
        this.resFood = resFood;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
