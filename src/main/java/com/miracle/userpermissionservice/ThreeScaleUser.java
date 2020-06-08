package com.miracle.userpermissionservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ThreeScaleUser {

    public int id;
    public String email;

    public void setEmail(String email){
        this.email = email;
    }


    public String getEmail(){
        return email;
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