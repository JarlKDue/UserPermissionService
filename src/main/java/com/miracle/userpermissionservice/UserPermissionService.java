package com.miracle.userpermissionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.naming.NamingException;


@SpringBootApplication
public class UserPermissionService {

    public static void main(String[] args) throws NamingException {
        SpringApplication.run(UserPermissionService.class, args);

    }
}
