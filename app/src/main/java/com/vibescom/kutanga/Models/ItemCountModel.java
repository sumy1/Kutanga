package com.vibescom.kutanga.Models;

import java.io.Serializable;

public class ItemCountModel extends BaseModel implements Serializable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String count;
    private String price;
    public ItemCountModel(String name,String count,String price){
        this.name=name;
        this.count=count;
        this.price=price;

    }
}
