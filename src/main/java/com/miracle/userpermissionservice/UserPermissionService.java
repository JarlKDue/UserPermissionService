package com.miracle.userpermissionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

@SpringBootApplication
public class UserPermissionService {

    public static void main(String[] args) throws NamingException {
        SpringApplication.run(UserPermissionService.class, args);


    final String ldapAdServer = "ldap://eniig.org:389";
    final String ldapSearchBase = "OU=3SCALE,OU=Funktioner,OU=Standard,DC=eniig,DC=org";

    final String ldapUsername = "OpenShift";
    final String ldapPassword = "Such$Y9t2RiBicuQ!p*j";

    Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
        env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapAdServer);

        env.put("java.naming.ldap.attributes.binary", "objectSID");


    LdapContext ctx = new InitialLdapContext();
    ActiveDirectorySearchInterface.shouldUserBeAdmin(ctx, ldapSearchBase, "test");

    }
}
