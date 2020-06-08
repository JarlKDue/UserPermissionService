package com.miracle.userpermissionservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ThreeScaleMessage {

    private String type;
    private String action;
    private ThreeScaleUser threeScaleUser;
    public ThreeScaleMessage(){};

    public void setType(String type){
        this.type =type;
    }

    public void setAction(String action){
        this.action = action;
    }

    public void setThreeScaleUser(ThreeScaleUser threeScaleUser){
        this.threeScaleUser = threeScaleUser;
    }

    public ThreeScaleUser getThreeScaleUser(){
        return threeScaleUser;
    }


}

