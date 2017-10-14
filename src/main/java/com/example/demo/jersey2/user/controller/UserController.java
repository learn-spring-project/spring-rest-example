package com.example.demo.jersey2.user.controller;

import com.example.demo.jersey2.user.domain.User;
import com.example.demo.jersey2.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Administrator on 2017/10/13.
 */
@Controller
@Path("/user")
public class UserController {
    @Autowired
    @Qualifier(value = "userService")
    private UserService userService;

    //@PathParam
    //请求地址：http://localhost:8080/user/zzh
    //返回数据：{"userId":1,"userName":"zzh"}
    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public User userInfo(@PathParam("param") String userName){
        User user = new User();
        user.setUserId(1);
        user.setUserName(userName);
        return  user;
    }

    //请求地址：http://localhost:8080/user/user/zzh
    //返回数据：Hello Jerseyzzh
    @GET
    @Path("/user/{param}")
    public String userInfo1(@PathParam("param")
                                    String userName) {
        String str = userService.say();
        return str + userName;
    }

     //请求地址：http://localhost:8080/user/user
    //RequestBody映射对象
    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //直接以对象的方式获取
    public User  update(User user){

        //User user1 = new User();
        user.setUserId(user.getUserId()+1);
        return user;
    }

    //@QueryParam
    //请求地址：http://localhost:8080/user/one?name=zzh
    @Path("/one")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String find(@QueryParam("name") String name)
    {
        return name;
    }

    //同上面的PathParam，也可以用UriInfo去获取QueryParam
    //请求地址：http://localhost:8080/user/one2?name=zzh
    @Path("/one2")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String find2(@Context UriInfo info)
    {
        return  info.getQueryParameters().getFirst("name");
    }

    //@HeaderParam
    //请求路径：http://localhost:8080/user/header
    //header username:zzh
    @Path("/header")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String header(@HeaderParam("username") String referer){
        return  referer;
    }

    //@CookieParam
    //返回类型为Response
    @Path("/cookie")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response cookie(@CookieParam("userId") int userId)
    {
        return  Response.status(200).entity("Response: " + userId).build();
    }

}
