package com.miracle.userpermissionservice;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;

public interface ThreeScaleApiInterface {

    static String threeScaleUrl = "http://intapisp-admin.intapisp.pr3scalec01.eniig.org/";
    static String threeScaleAccessToken = "npuy3x6a0h6his7o";




    static boolean setUserToAdmin(String userId){
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPut request = new HttpPut(threeScaleUrl + "admin/api/users/" + userId + "/admin.xml");
            request.setHeader("access_token", threeScaleAccessToken);
            HttpResponse response = client.execute(request);
            System.out.println(response);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean updateUserMemberPermissions(){
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPut request = new HttpPut(threeScaleUrl + "admin/api/users/10/permissions.xml");
            request.setHeader("access_token", threeScaleAccessToken);
            HttpResponse response = client.execute(request);
            System.out.println(response);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;    }
}
