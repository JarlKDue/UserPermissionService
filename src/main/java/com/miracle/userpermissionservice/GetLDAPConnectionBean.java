package com.miracle.userpermissionservice;

import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Optional;
import java.util.Properties;

@Component
public class GetLDAPConnectionBean {

    public Optional<DirContext> getDirContext() {
        final String ldapAdServer = "ldap://eniig.org:389";
        final String ldapPassword = "Such$Y9t2RiBicuQ!p*j";
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.SECURITY_PRINCIPAL, "CN=sPROSLDAP,OU=OpenShift,OU=Servicebrugere,OU=Standard,DC=eniig,DC=org");
        properties.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        properties.put(Context.SECURITY_PROTOCOL, "simple");
        properties.put(Context.PROVIDER_URL, ldapAdServer);
        properties.put("java.naming.ldap.attributes.binary", "objectSID");
        try {
            return Optional.of(new InitialDirContext(properties));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        } catch (NamingException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}