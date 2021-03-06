package com.example.demo;

import com.example.demo.jersey2.user.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =SpringRestfulExampleApplication.class )
public class JerseyClientTest {

    @Test
    public  void findUser(){
        String url="http://localhost:8080/user";
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url).path("2").queryParam("test");
        Invocation.Builder builder = target.request().header("someHead", "true");
        Response response = builder.get();
        User user = response.readEntity(User.class);
        System.out.println(user);
        response.close();

        /**
         * 如果某些异构的服务端只能返回List类型的POJO集合数据，此时可以使用GenericType类型来解决
         * 示例代码
         GenericType<List<User>> genericType = new GenericType<List<User>>(){};
         List<User> users = builder.get(genericType);
         */

    }
    @Test
    public  void addUser(){
        String url="http://localhost:8080/user/new";
        System.out.println("****增加用户addUser****");
        User user = new User("zzh2", 2);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder builder = target.request();
       final  User user1 = builder.post(Entity.entity(user, MediaType.APPLICATION_JSON),User.class);
       System.out.println(user1);
   //     User user1 =

    }
}
