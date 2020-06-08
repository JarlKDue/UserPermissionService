package com.miracle.userpermissionservice;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

public interface ActiveDirectorySearchInterface {

    static boolean shouldUserBeAdmin(DirContext ctx, String ldapSearchBase, String accountName) throws NamingException {
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);
        SearchResult searchResult = null;
        System.out.println(results.toString());
        if(results.hasMoreElements()) {
            searchResult = (SearchResult) results.nextElement();

            //make sure there is not another item available, there should be only 1 match
            if(results.hasMoreElements()) {
                System.err.println("Matched multiple users for the accountName: " + accountName);
                return false;
            }
        }
        System.out.println("Returning True!!");
        return true;
    }
}
