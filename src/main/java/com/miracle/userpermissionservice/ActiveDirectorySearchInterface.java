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
        List<String> externalEmails = getExternalEmails(ctx, group);
        List<String> internalEmails = getInternalEmails(ctx, group);
        System.out.println(externalEmails);
        System.out.println(internalEmails);
        externalEmails.addAll(internalEmails);
        return externalEmails;
    }

    static List<String> getInternalEmails(DirContext ctx, String group) {
        NamingEnumeration<SearchResult> results;
        List<String> memberEmails = new ArrayList<>();
        String searchFilter = "(&(objectCategory=user)(memberOf:1.2.840.113556.1.4.1941:=CN=" + group + ",OU=3SCALE,OU=Funktioner,OU=Standard,DC=eniig,DC=org))";
        String[] reqAtt = { "userPrincipalName"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);
        try {
            System.out.println("Trying to Fetch Internal Users in " + group);
            results = completeSearchForInternalUsers(ctx, searchFilter, controls);
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

    static List<String> getExternalEmails(DirContext ctx, String group) {
        NamingEnumeration<SearchResult> results;
        List<String> memberEmails = new ArrayList<>();
        String searchFilter = "(&(objectCategory=user)(memberOf:1.2.840.113556.1.4.1941:=CN=" + group + ",OU=3SCALE,OU=Funktioner,OU=Standard,DC=eniig,DC=org))";
        String[] reqAtt = { "userPrincipalName"};
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(reqAtt);
        try {
            System.out.println("Trying to Fetch External Users in " + group);
            results = completeSearchForExternalUsers(ctx, searchFilter, controls);
            assert results != null;
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

    static NamingEnumeration<SearchResult> completeSearchForInternalUsers(DirContext ctx, String searchFilter, SearchControls controls){
        try{
            return ctx.search("OU=*,DC=eniig,DC=org", searchFilter, controls);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    static NamingEnumeration<SearchResult> completeSearchForExternalUsers(DirContext ctx, String searchFilter, SearchControls controls){
        try{
            return ctx.search("OU=Eksterne,DC=eniig,DC=org", searchFilter, controls);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }
    static String extractPrincipalNameFromUserPrincipalName(String userPrincipalName){
        return userPrincipalName.substring(19);
    }
}
