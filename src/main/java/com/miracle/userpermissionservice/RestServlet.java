package com.miracle.userpermissionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
public class RestServlet {

    @Autowired
    GetLDAPConnectionBean getLDAPConnectionBean;

    @PostMapping(value = "/update_user_permissions", consumes = "application/xml")
    public void updateUserPermissions(@RequestBody String threeScaleMessage){
        System.out.println("Received Request to Validate " + threeScaleMessage);
        getLDAPConnectionBean.getDirContext().ifPresent(
                context -> ActiveDirectorySearchInterface.shouldUserBeAdmin(context, threeScaleMessage)
        );
    }

    @GetMapping(value = "check_health")
    public void checkHealth(){
        String incoming = "Test";
        System.out.println("Alive and Kicking");
        System.out.println("Received Request to Validate " + incoming);
        getLDAPConnectionBean.getDirContext().ifPresent(
                context -> ActiveDirectorySearchInterface.shouldUserBeAdmin(context, incoming)
        );
    }


    public String fetchUserNameFromXMLSchema(String xml){
        return xml;
    }
}
