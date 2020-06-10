package com.miracle.userpermissionservice;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.List;

public interface ActiveDirectorySearchInterface {

    static List<String> getMembersOf3ScaleGroups(DirContext ctx, String group){
        List<String> memberEmails = new ArrayList<>();
        String searchFilter = "(memberOf:1.2.840.113556.1.4.1941:=CN=" + group + ",OU=3SCALE,OU=Funktioner,OU=Standard,DC=eniig,DC=org)";
        String[] reqAtt = { "email"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);
        try {
            System.out.println("Trying to Fetch Users");
            NamingEnumeration users = ctx.search("OU=Standard,DC=eniig,DC=org", searchFilter, controls);
            SearchResult result = null;
            System.out.println(users.toString());
            while (users.hasMore()) {
                System.out.println(users.next().toString());
                //Add user email to list, return list.

            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return memberEmails;
    }
}
