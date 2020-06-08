package com.miracle.userpermissionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class RestServlet {

    @Autowired
    GetLDAPConnectionBean getLDAPConnectionBean;

    @PostMapping(value = "/update_user_permissions")
    public void updateUserPermissions(@RequestParam String incoming){
        System.out.println("Received Request to Validate " + incoming);
        getLDAPConnectionBean.getDirContext().ifPresent(
                context -> ActiveDirectorySearchInterface.shouldUserBeAdmin(context, incoming)
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
