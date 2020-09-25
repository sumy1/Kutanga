package com.vibescom.kutanga.Models;

import java.io.Serializable;

public class IntroModel extends BaseModel implements Serializable {

    private int image;
    private String head,desc;

    public IntroModel(int image, String head, String desc){
        this.image = image;
        this.head = head;
        this.desc = desc;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
