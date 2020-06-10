package com.miracle.userpermissionservice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.junit.jupiter.api.Test;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

class UserPermissionServiceTests {

    @Test
    public void jacksonObjectMapperTest() throws JsonProcessingException {
        Users userList = new Users();
        userList.addUsers(new User(4,12, "testing@testtest.com"));
        userList.addUsers(new User(9,23, "testemail@testtest.com"));
        userList.addUsers(new User(2,5, "test@test.com"));
        XmlMapper xmlMapper = (XmlMapper) new XmlMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        System.out.println(xmlMapper.writeValueAsString(userList));

        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                " <users>\n" +
                " <user>\n" +
                " <id>2</id>\n" +
                " <created_at>2020-03-20T11:39:50+01:00</created_at>\n" +
                " <updated_at>2020-03-20T11:39:50+01:00</updated_at>\n" +
                " <account_id>2</account_id>\n" +
                " <state>active</state>\n" +
                " <role>admin</role>\n" +
                " <username>admin</username>\n" +
                " <email>admin@intapisp.intapisp.pr3scalec01.eniig.org</email>\n" +
                " <extra_fields></extra_fields>\n" +
                " </user>\n" +
                " <user>\n" +
                " <id>6</id>\n" +
                " <created_at>2020-05-13T10:14:59+02:00</created_at>\n" +
                " <updated_at>2020-05-13T10:22:04+02:00</updated_at>\n" +
                " <account_id>2</account_id>\n" +
                " <state>active</state>\n" +
                " <role>admin</role>\n" +
                " <username>lardav@norlys.dk</username>\n" +
                " <email>lardav@norlys.dk</email>\n" +
                " <extra_fields></extra_fields>\n" +
                " </user>\n" +
                " </users>";

        String newXMLString = xmlString.replaceAll("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n", "");
       Users users2 = xmlMapper.readValue(newXMLString, Users.class);
       users2.toString();


    }

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
class User{

    User(){};
    User(int id, int account_id, String email){
        this.id = id;
        this.account_id = account_id;
        this.email = email;
    }

    private String email;
    private int id;
    private int account_id;

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
@JacksonXmlRootElement(localName = "users")
class Users{
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "user")
    public List<User> users = new ArrayList<>();

    public void addUsers(User user){
        users.add(user);
    }
}