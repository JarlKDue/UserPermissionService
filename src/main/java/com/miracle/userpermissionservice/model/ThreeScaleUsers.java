package com.miracle.userpermissionservice.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "users")
public class ThreeScaleUsers {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "user")
        public List<ThreeScaleUser> users = new ArrayList<>();

        public void addUsers(ThreeScaleUser user){
            users.add(user);
        }
}
