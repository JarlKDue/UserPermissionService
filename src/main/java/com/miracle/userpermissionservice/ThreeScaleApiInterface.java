package com.miracle.userpermissionservice;


import com.sun.jndi.toolkit.url.Uri;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ThreeScaleApiInterface {

    static String threeScaleUrl = "https://intapisp-admin.intapisp.pr3scalec01.eniig.org/";
    static String threeScaleAccessToken = "npuy3x6a0h6his7o";


    static boolean syncAdminProviderUser(String email){
            String userId = "userIdFromResponse";
            createProviderUser(email);
            setUserToAdmin(userId);
            activateAccount(userId);
            return true;
    }

    static boolean syncManagerProviderUser(String email){
        String userId = "userIdFromResponse";
        createProviderUser(email);
        updateUserMemberPermissions(userId);
        activateAccount(userId);
        return true;    }

    static String createProviderUser(String email){
        try {
            HttpClient httpClient = getHttpClient();

            HttpPost request = new HttpPost(threeScaleUrl + "admin/api/users.xml");
            URI uri = new URIBuilder(request.getURI())
                    .addParameter("access_token", threeScaleAccessToken)
                    .addParameter("email", email)
                    .addParameter("username", "TestUsername")
                    .addParameter("password", "TestPassword1234")
                    .build();
            request.setURI(uri);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getLastHeader("Location"));
            String userId = extractUserIdFromLocation(response.getLastHeader("Location").getValue());
            System.out.println(userId);
        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | URISyntaxException | KeyManagementException e) {
            e.printStackTrace();
        }
        return "not created";
    }

    static boolean setUserToAdmin(String userId) {

        try {
            HttpClient httpClient = getHttpClient();
            HttpPut request = new HttpPut(threeScaleUrl + "admin/api/users/" + userId + "/admin.xml");
            request.setHeader("access_token", threeScaleAccessToken);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);
            return true;

        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean activateAccount(String userId){
        try {
            HttpClient httpClient = getHttpClient();
                         HttpPut request = new HttpPut(threeScaleUrl + "admin/api/users/" + userId + "/activate.xml");
            request.setHeader("access_token", threeScaleAccessToken);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getLastHeader("Location").getValue().s);
            return true;

        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean updateUserMemberPermissions(String userId){
        try {
            HttpClient httpClient = getHttpClient();
            HttpPut request = new HttpPut(threeScaleUrl + "admin/api/users/" + userId + "/permissions.xml");
            request.setHeader("access_token", threeScaleAccessToken);
            request.setHeader("allowed_service_ids", "allowed_service_ids[]");
            request.setHeader("allowed_sections", threeScaleAccessToken);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);
            return true;

        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean syncAdminProviders(List<String> f_3SCALE_administrator) {
        for(String email : f_3SCALE_administrator){
            syncAdminProviderUser(email);
        }
        return true;
    }

    static boolean syncManagerProviderUsers(List<String> f_3SCALE_api_manager) {
        for(String email : f_3SCALE_api_manager){
            syncManagerProviderUser(email);
        }
        return true;
    }

    static HttpClient getHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return HttpClients
                .custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
    }

    static String extractUserIdFromLocation(String location){
        Pattern p = Pattern.compile("/id=(.*?)/&");
        Matcher m = p.matcher(location);
        return m.group(1);
    }
}
