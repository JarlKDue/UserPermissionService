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

//<?xml version="1.0" encoding="UTF-8"?>
//<event>
//<type>application</type>
//<action>updated</action>
//<object>
//<user>
//<id>9</id>
//<created_at>2020-06-08T20:48:17+02:00</created_at>
//<updated_at>2020-06-08T20:48:18+02:00</updated_at>
//<account_id>6</account_id>
//<state>active</state>
//<role>admin</role>
//<username>test</username>
//<email>test@email.com</email>
//<extra_fields></extra_fields>
////</user>
//</object>
//</event>