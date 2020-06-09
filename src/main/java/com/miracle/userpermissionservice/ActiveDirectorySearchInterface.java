package com.miracle.userpermissionservice;

import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

public interface ActiveDirectorySearchInterface {

    static boolean shouldUserBeAdmin(DirContext ctx, String accountName) {
        System.out.println("Checking if User Should be Admin");
        String searchFilter = "(objectClass=Person)";
        String[] reqAtt = { "sAMAccountName"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        controls.setReturningAttributes(reqAtt);

        try {
            System.out.println("Trying to Fetch Users");
            NamingEnumeration users = ctx.search("OU=3SCALE,OU=Funktioner,OU=Standard,DC=eniig,DC=org", searchFilter, controls);
            SearchResult result = null;
            System.out.println(users.hasMore());
            while (users.hasMore()) {
                result = (SearchResult) users.next();
                Attributes attr = result.getAttributes();
                System.out.println(attr.get("sAMAccountName"));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }


//        String searchFilter = "(objectClass=inetOrgPerson)";
//        final String ldapSearchBase = "OU=3SCALE,OU=Funktioner,OU=Standard,DC=eniig,DC=org";
//
//        SearchControls searchControls = new SearchControls();
//        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//
//        try {
//            NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);
//            SearchResult searchResult = null;
//            if(results.hasMoreElements()) {
//                searchResult = results.nextElement();
//                System.out.println(searchResult.toString());
//
//                if(results.hasMoreElements()) {
//                    System.err.println("Matched multiple users for the accountName: " + accountName);
//                    return false;
//                }
//            }
//            return true;
//
//        } catch (NamingException e){
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("Returning True!!");
        return true;
    }
}
