package com.miracle.userpermissionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

@Component
@RestController
public class RestServlet {

    @Autowired
    GetLDAPConnectionBean getLDAPConnectionBean;

    @PostMapping("/update_user_permissions")
    public void updateUserPermissions(@RequestBody ThreeScaleEvent threeScaleEvent)  {
//        String email = fetchUserNameFromXMLSchema(threeScaleMessage, "//email[1]/text()");
//        String userId = fetchUserNameFromXMLSchema(threeScaleMessage, "//users/user/id/text()");
//        System.out.println("Received Request to Validate " + email);
//        System.out.println("Received Request to Validate " + userId);
        getLDAPConnectionBean.getDirContext().ifPresent(
                context -> ActiveDirectorySearchInterface.shouldUserBeAdmin(context, threeScaleEvent.getThreeScaleUser().getEmail())
        );
        System.out.println(ThreeScaleApiInterface.setUserToAdmin());
    }

    @GetMapping("/check_health")
    public void checkHealth(){
        String incoming = "Test";
        System.out.println("Alive and Kicking");
        System.out.println("Received Request to Validate " + incoming);
        getLDAPConnectionBean.getDirContext().ifPresent(
                context -> ActiveDirectorySearchInterface.shouldUserBeAdmin(context, incoming)
        );
    }


    public String fetchUserNameFromXMLSchema(String xml, String expression){
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(new InputSource(new StringReader(xml)));
            System.out.println(document.getXmlVersion());

            XPath xPath = XPathFactory.newInstance().newXPath();
            Node node = (Node) xPath.compile(expression).evaluate(document, XPathConstants.NODE);
            return node.getNodeValue();
        } catch (ParserConfigurationException | IOException | XPathExpressionException | SAXException e) {
            e.printStackTrace();
        }
        return "Could not parse";
    }

}
