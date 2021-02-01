package com.miracle.userpermissionservice;

import com.sun.jndi.toolkit.dir.SearchFilter;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.List;

public interface ActiveDirectorySearchInterface {

    List<String> basePaths = new ArrayList<>();


    static List<String> getMembersOf3ScaleGroups(DirContext ctx, String group){
//        basePaths.add("OU=Eniig_Faellesfunktioner,DC=eniig,DC=org");
        basePaths.add("OU=Standard,DC=eniig,DC=org");
        basePaths.add("OU=Eksterne,DC=eniig,DC=org");
        List<String> membersFound = new ArrayList<>();
        for(String basePath : basePaths){
            membersFound.addAll(getMembers(ctx, group, basePath));
        }
        testGetAllMembers(ctx, "ou=3SCALE");
        System.out.println(membersFound);
        return membersFound;
    }

    static void testGetAllMembers(DirContext ctx, String searchFilter){
        NamingEnumeration<SearchResult> results;
        List<String> memberEmails = new ArrayList<>();
        String[] reqAtt = { "userPrincipalName"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);
        try {
            System.out.println("Trying to Fetch Internal Users in " + searchFilter);
            results = searchForUsers("DC=eniig,DC=org",ctx, searchFilter, controls);
            while (results.hasMore()) {
                SearchResult result = results.next();
                System.out.println(result.toString());
                Attributes attrs = result.getAttributes();
                memberEmails.add(extractPrincipalNameFromUserPrincipalName(attrs.get("userPrincipalName").toString()));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        System.out.println(memberEmails);
    }

    static List<String> getMembers(DirContext ctx, String group, String ou) {
        NamingEnumeration<SearchResult> results;
        List<String> memberEmails = new ArrayList<>();
        String searchFilter = "(&(objectCategory=user)(memberOf:1.2.840.113556.1.4.1941:=CN=" + group + ",OU=3SCALE,OU=Funktioner,OU=Standard,DC=eniig,DC=org))";
        String[] reqAtt = { "userPrincipalName"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);
        try {
            System.out.println("Trying to Fetch Internal Users in " + group);
            results = searchForUsers(ou + ",DC=eniig,DC=org",ctx, searchFilter, controls);
            while (results.hasMore()) {
                SearchResult result = results.next();
                System.out.println(result.toString());
                Attributes attrs = result.getAttributes();
                memberEmails.add(extractPrincipalNameFromUserPrincipalName(attrs.get("userPrincipalName").toString()));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return memberEmails;
    }

    static NamingEnumeration<SearchResult> searchForUsers(String basePath,DirContext ctx, String searchFilter, SearchControls controls){
        try{
            return ctx.search(basePath, searchFilter, controls);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String extractPrincipalNameFromUserPrincipalName(String userPrincipalName){
        return userPrincipalName.substring(19);
    }
}
