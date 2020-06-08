package com.miracle.userpermissionservice;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class RestServlet {


    @PostMapping(value = "/update_user_permissions", consumes = "applicat/xml")
    public void updateUserPermissions(@RequestParam String incoming){
        System.out.println(incoming);
    }

    @GetMapping(value = "check_health")
    public void checkHealth(){
        System.out.println("Alive and Kicking");
    }
}
