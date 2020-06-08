package com.miracle.userpermissionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

@RestController
@Component
public class RestServlet {

    @Autowired
    GetLDAPConnectionBean getLDAPConnectionBean;

    @PostMapping(value = "/update_user_permissions", consumes = "application/xml")
    public void updateUserPermissions(@RequestBody String threeScaleMessage) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile("//email[1]/text()").evaluate(threeScaleMessage, XPathConstants.NODESET);
        System.out.println("Received Request to Validate " + nodeList.item(0).getNodeValue());
        getLDAPConnectionBean.getDirContext().ifPresent(
                context -> ActiveDirectorySearchInterface.shouldUserBeAdmin(context, threeScaleMessage)
        );
    }

    @GetMapping(value = "check_health")
    public void checkHealth(){
        String incoming = "Test";
        System.out.println("Alive and Kicking");
        System.out.println("Received Request to Validate " + incoming);
        getLDAPConnectionBean.getDirContext().ifPresent(
                context -> ActiveDirectorySearchInterface.shouldUserBeAdmin(context, incoming)
        );
    }


    public String fetchUserNameFromXMLSchema(String xml){
        return xml;
    }
}
