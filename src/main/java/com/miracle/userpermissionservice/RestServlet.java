package com.miracle.userpermissionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    @GetMapping("/check_health")
    public void checkHealth(){
        System.out.println("Alive and Kicking");
    }

    @GetMapping("/create_admin")
    public void createAdmin(){
        System.out.println("Creating Admin");
        ThreeScaleApiInterface.syncAdminProviderUser("testAdmin@test.com");
    }

    @GetMapping("/create_manager")
    public void createManager(){
        System.out.println("Creating Manager");
        ThreeScaleApiInterface.syncManagerProviderUser("testManager@test.com");
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

    @GetMapping("/sync_users")
    public void syncUsers(){
        getLDAPConnectionBean.getDirContext().ifPresent(
                context ->ThreeScaleApiInterface.syncAdminProviders(ActiveDirectorySearchInterface.getMembersOf3ScaleGroups(context, "r_3SCALE_Administrator"))
        );
        getLDAPConnectionBean.getDirContext().ifPresent(
                context -> ThreeScaleApiInterface.syncManagerProviderUsers(ActiveDirectorySearchInterface.getMembersOf3ScaleGroups(context, "r_3SCALE_API_Manager"))
                );
    }

}
