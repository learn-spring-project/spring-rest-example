package com.example.demo.jersey2.echo.controller;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2017/10/13.
 */
@Component("echoControllerJersey")
@Path("/echo")
public class EchoController {
    @Path("/hello")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(){
        return "hello jersey";
    }
}
