package com.miracle.userpermissionservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="event")
public class ThreeScaleEvent {

    private String type;
    private String action;
    private ThreeScaleUser threeScaleUser;
    public ThreeScaleEvent(){};

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

