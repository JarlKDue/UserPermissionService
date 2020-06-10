package com.miracle.userpermissionservice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.miracle.userpermissionservice.model.ThreeScaleUser;
import com.miracle.userpermissionservice.model.ThreeScaleUsers;
import com.sun.jndi.toolkit.url.Uri;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public interface ThreeScaleApiInterface {

    static String threeScaleUrl = "https://intapisp-admin.intapisp.pr3scalec01.eniig.org/";
    static String threeScaleAccessToken = "npuy3x6a0h6his7o";


    static boolean syncAdminProviderUser(String email){
            String userId = createProviderUser(email);
            if(userId!=null) {
                setUserToAdmin(userId);
                activateAccount(userId);
            } else System.out.println("User already exists");
            return true;
    }

    static boolean syncManagerProviderUser(String email){
        String userId = createProviderUser(email);
        if(userId!=null) {
            updateUserMemberPermissions(userId);
            activateAccount(userId);
        } else System.out.println("User already exists");
        return true;    }

    static String createProviderUser(String email){
        try {
            HttpClient httpClient = getHttpClient();
            HttpPost request = new HttpPost(threeScaleUrl + "admin/api/users.xml");
            URI uri = new URIBuilder(request.getURI())
                    .addParameter("access_token", threeScaleAccessToken)
                    .addParameter("email", email)
                    .addParameter("username", email)
                    .addParameter("password", "TestPassword1234")
                    .build();
            request.setURI(uri);
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode()==201){
                String userId = extractUserIdFromLocation(response.getLastHeader("Location").getValue());
                System.out.println(userId);
                return userId;
            } else return null;

        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | URISyntaxException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    static boolean setUserToAdmin(String userId) {

        try {
            HttpClient httpClient = getHttpClient();
            HttpPut request = new HttpPut(threeScaleUrl + "admin/api/users/" + userId + "/admin.xml");
            URI uri = new URIBuilder(request.getURI())
                    .addParameter("access_token", threeScaleAccessToken)
                    .build();
            request.setURI(uri);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);
            return true;

        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean activateAccount(String userId){
        try {
            HttpClient httpClient = getHttpClient();
            HttpPut request = new HttpPut(threeScaleUrl + "admin/api/users/" + userId + "/activate.xml");
            URI uri = new URIBuilder(request.getURI())
                    .addParameter("access_token", threeScaleAccessToken)
                    .build();
            request.setURI(uri);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);
            return true;

        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | KeyStoreException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean updateUserMemberPermissions(String userId){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("allowed_sections[]", "portal"));
        params.add(new BasicNameValuePair("allowed_sections[]", "finance"));
        params.add(new BasicNameValuePair("allowed_sections[]", "settings"));
        params.add(new BasicNameValuePair("allowed_sections[]", "partners"));
        params.add(new BasicNameValuePair("allowed_sections[]", "monitoring"));
        params.add(new BasicNameValuePair("allowed_service_ids[]", ""));
        try {
            HttpClient httpClient = getHttpClient();
            HttpPut request = new HttpPut(threeScaleUrl + "admin/api/users/" + userId + "/permissions.xml");
            URI uri = new URIBuilder(request.getURI())
                    .addParameter("access_token", threeScaleAccessToken)
                    .addParameter("allowed_service_ids[]", "")
                    .build();
            request.setURI(uri);
            request.setEntity(new UrlEncodedFormEntity(params));
            request.setHeader("Content-Type", "application/x-www-form-urlencoded");
            System.out.println(uri);
            HttpResponse response = httpClient.execute(request);
            String responseString = new BasicResponseHandler().handleEntity(response.getEntity());
            System.out.println(responseString);
            System.out.println(response);
            return true;

        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean syncAdminProviders(List<String> f_3SCALE_administrator) {
        System.out.println("Syncing Admins");
        for(String email : f_3SCALE_administrator){
            syncAdminProviderUser(email);
        }
        return true;
    }

    static boolean syncManagerProviderUsers(List<String> f_3SCALE_api_manager) {
        System.out.println("Syncing Managers");
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
        String[] splittedLocation = location.split("id=");
        String id = splittedLocation[1].split("&")[0];
        return id;
    }

    /**
     <?xml version="1.0" encoding="UTF-8"?>
     <users>
     <user>
     <id>2</id>
     <created_at>2020-03-20T11:39:50+01:00</created_at>
     <updated_at>2020-03-20T11:39:50+01:00</updated_at>
     <account_id>2</account_id>
     <state>active</state>
     <role>admin</role>
     <username>admin</username>
     <email>admin@intapisp.intapisp.pr3scalec01.eniig.org</email>
     <extra_fields></extra_fields>
     </user>
     <user>
     <id>6</id>
     <created_at>2020-05-13T10:14:59+02:00</created_at>
     <updated_at>2020-05-13T10:22:04+02:00</updated_at>
     <account_id>2</account_id>
     <state>active</state>
     <role>admin</role>
     <username>lardav@norlys.dk</username>
     <email>lardav@norlys.dk</email>
     <extra_fields></extra_fields>
     </user>
     </users>
     */
    static boolean removeUsersNoLongerInGroups(){
        //For admin users, fetch all admin users from 3scale, build new list of IDS based on difference
        //Send list to delete method
        //Same for Managers
        System.out.println(fetchAllProviderUsers());
        return true;
    }

    static ThreeScaleUsers fetchAllProviderUsers(){
        try {
            HttpClient httpClient = getHttpClient();
            HttpGet request = new HttpGet(threeScaleUrl + "admin/api/users.xml");
            URI uri = new URIBuilder(request.getURI())
                    .addParameter("access_token", threeScaleAccessToken)
                    .build();
            request.setURI(uri);
            HttpResponse response = httpClient.execute(request);
            String responseString = new BasicResponseHandler().handleEntity(response.getEntity());
            ThreeScaleUsers threeScaleUsers = generateThreeScaleUsersFromProviderResponse(responseString);
            System.out.println(response);
            return threeScaleUsers;

        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | KeyStoreException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    static ThreeScaleUsers generateThreeScaleUsersFromProviderResponse(String response) throws JsonProcessingException {
        XmlMapper xmlMapper = (XmlMapper) new XmlMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String newXMLString = response.replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n", "");
        ThreeScaleUsers users2 = xmlMapper.readValue(newXMLString, ThreeScaleUsers.class);
        return users2;
    }
}
