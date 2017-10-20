package com.example.demo.jersey2.user.controller;

import com.example.demo.jersey2.user.domain.User;
import com.example.demo.jersey2.user.domain.UserXML;
import com.example.demo.jersey2.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/13.
 */
@Controller
@Path("/user")
public class UserController {
    private static Map<Integer, UserXML> userXmlMap = new HashMap<Integer, UserXML>();

    @Autowired
    @Qualifier(value = "userService")
    private UserService userService;

    //@PathParam
    //请求地址：http://localhost:8080/user/new body: {"userId":1,"userName":"zzh"}
    //返回数据：{"userId":1,"userName":"zzh"}
    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    //XML
    //请求地址：http://localhost:8080/user/xml?id=1
    //返回数据：
    //<userXML><userId>1</userId><userName>test</userName></userXML>
    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_XML)
    public  UserXML addUserXML(@QueryParam("id") Integer id)
    {
        UserXML userXML = new UserXML();
        userXML.setUserId(id);
        userXML.setUserName("test");
      //  userXmlMap.put(id, userXML);
        return  userXML;
    }

    //请求地址：http://localhost:8080/user/string?name=zzh
    //返回数据：zzh
    @GET
    @Path("/string")
    @Produces(MediaType.APPLICATION_JSON)
    public String string(@QueryParam("name")
                                  String name) {
        return name;
    }



    //请求地址：http://localhost:8080/user/1
    //返回数据：{"userId":1,"userName":"zzh"}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User userInfo1(@PathParam("id")
                                  Integer id) {
        return userService.findById(id);
    }

    //请求地址：http://localhost:8080/user/fresh
    //RequestBody映射对象
    @PUT
    @Path("/fresh")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //直接以对象的方式获取
    public User update(User user) {
        return userService.updateUser(user);
    }

    //@QueryParam
    //请求地址：http://localhost:8080/user/user?id=1
    @Path("/user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@QueryParam("id") Integer id) {
        return userService.findById(id);
    }

    //同上面的PathParam，也可以用UriInfo去获取QueryParam
    //请求地址：http://localhost:8080/user?id=1
    @Path("/id")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User find2(@Context UriInfo info) {
        return userService.findById(Integer.parseInt(info.getQueryParameters().getFirst("id")));
    }

    //@HeaderParam
    //请求路径：http://localhost:8080/user/header
    //header username:zzh
    @Path("/header")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response header(@HeaderParam("username") String referer) {
       // return referer;
        return Response.status(200).entity("Response: " + referer).header("xxx", "value").build();
    }

    //@CookieParam
    //返回类型为Response
    @Path("/cookie")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response cookie(@CookieParam("userId") int userId) {
        return Response.status(200).entity("Response: " + userId).build();
    }

}
