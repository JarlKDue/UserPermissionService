package com.miracle.userpermissionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.naming.directory.DirContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RestController
public class RestServlet {

    public static final String THREESCALEADMINSADGROUP = "f_3SCALE_Administrator";
    public static final String THREESCALEMANAGERSADGROUP = "f_3SCALE_API_Manager";

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
        Optional<DirContext> ctx = getLDAPConnectionBean.getDirContext();
        if(ctx.isPresent()){
            List<String> adminEmails = ActiveDirectorySearchInterface.getMembersOf3ScaleGroups(ctx.get(), THREESCALEADMINSADGROUP);
            ThreeScaleApiInterface.syncAdminProviders(adminEmails);
            List<String> managerEmails = ActiveDirectorySearchInterface.getMembersOf3ScaleGroups(ctx.get(), THREESCALEMANAGERSADGROUP);
            ThreeScaleApiInterface.syncManagerProviderUsers(managerEmails);
            List<String> combinedEmails = new ArrayList<>();
            combinedEmails.addAll(adminEmails);
            combinedEmails.addAll(managerEmails);
            ThreeScaleApiInterface.removeUsersNoLongerInGroups(combinedEmails);
        }
    }

    @GetMapping("/test_sync_users")
    public void testSyncUsers(){
        List<String> internalEmails = new ArrayList<>();
        internalEmails.add("testInternalEmail1@test.com");
        internalEmails.add("testInternalEmail2@test.com");
        internalEmails.add("testInternalEmail3@test.com");
        internalEmails.add("testInternalEmail4@test.com");
        List<String> externalEmails = new ArrayList<>();
        externalEmails.add("testExternalEmail1@test.com");
        externalEmails.add("testExternalEmail2@test.com");
        externalEmails.add("testExternalEmail3@test.com");
        externalEmails.add("testExternalEmail4@test.com");
        ThreeScaleApiInterface.syncAdminProviders(internalEmails);
        ThreeScaleApiInterface.syncManagerProviderUsers(externalEmails);
    }

    @GetMapping("/test_delete_users")
    public void testDeleteUsers(){
        Optional<DirContext> ctx = getLDAPConnectionBean.getDirContext();
        if(ctx.isPresent()){
            List<String> adminEmails = ActiveDirectorySearchInterface.getMembersOf3ScaleGroups(ctx.get(), THREESCALEADMINSADGROUP);
            List<String> managerEmails = ActiveDirectorySearchInterface.getMembersOf3ScaleGroups(ctx.get(), THREESCALEMANAGERSADGROUP);
            adminEmails.addAll(managerEmails);
            ThreeScaleApiInterface.removeUsersNoLongerInGroups(adminEmails);
        }
    }

}
