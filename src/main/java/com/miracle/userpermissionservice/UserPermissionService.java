package com.miracle.userpermissionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

@SpringBootApplication
public class UserPermissionService {

    public static void main(String[] args) throws NamingException {
        SpringApplication.run(UserPermissionService.class, args);

    }
}
