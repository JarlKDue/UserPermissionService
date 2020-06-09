package com.miracle.userpermissionservice;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.List;

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
            NamingEnumeration users = ctx.search("OU=Standard,DC=eniig,DC=org", searchFilter, controls);
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

    static List<Object> getMembersOf3ScaleGroups(DirContext ctx, String group){
        String searchFilter = "(memberOf:1.2.840.113556.1.4.1941:=CN=" + group + ",OU=3SCALE,OU=Funktioner,OU=Standard,DC=eniig,DC=org)";
        String[] reqAtt = { "email"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);
        try {
            System.out.println("Trying to Fetch Users");
            NamingEnumeration users = ctx.search("OU=Standard,DC=eniig,DC=org", searchFilter, controls);
            SearchResult result = null;
            System.out.println(users.hasMore());
            while (users.hasMore()) {
                System.out.println(users.next().toString());
                result = (SearchResult) users.next();
                Attributes attr = result.getAttributes();
                System.out.println(attr.get("email"));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
