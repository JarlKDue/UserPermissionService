package com.miracle.userpermissionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
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

        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.SECURITY_PRINCIPAL, "CN=sPROSLDAP,OU=OpenShift,OU=Servicebrugere,OU=Standard,DC=eniig,DC=org");
        properties.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        properties.put(Context.PROVIDER_URL, ldapAdServer);
        properties.put("java.naming.ldap.attributes.binary", "objectSID");


        LdapContext ctx = new InitialLdapContext();
        DirContext context = new InitialDirContext(properties);
    ActiveDirectorySearchInterface.shouldUserBeAdmin(ctx, ldapSearchBase, "test");

    }
}
