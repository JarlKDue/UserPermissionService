package com.miracle.userpermissionservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="object")
public class ThreeScaleUser {

    private String email;

    public void setEmail(String email){
        this.email = email;
    }


    public String getEmail(){
        return email;
    }

}


