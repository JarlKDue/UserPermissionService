package com.miracle.userpermissionservice;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import sun.misc.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public interface ThreeScaleApiInterface {

    static String threeScaleUrl = "http://intapisp-admin.intapisp.pr3scalec01.eniig.org/";
    static String threeScaleAccessToken = "npuy3x6a0h6his7o";




    static boolean setUserToAdmin(){
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPut request = new HttpPut(threeScaleUrl + "admin/api/users/10/admin.xml");
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
        return true;
    }
}
