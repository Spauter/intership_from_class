package com.bs.bean;


import java.io.Serializable;

public class CartItem implements Serializable {
    private ResFood resFood;
    private Integer num;
    private Double smallCount;
    private static final long serialVersionUID =-141093992802872751L;
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

    public void setSmallCount(Double smallCount) {
        this.smallCount = smallCount;
    }
}
