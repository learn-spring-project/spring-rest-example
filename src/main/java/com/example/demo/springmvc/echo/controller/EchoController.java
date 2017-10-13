package com.example.demo.springmvc.echo.controller;

import com.example.demo.springmvc.echo.domain.Echo;
import com.example.demo.springmvc.echo.domain.Message;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2017/10/12.
 */
@RestController
@RequestMapping("/echo")
public class EchoController {
    private static final String echoTemplate1 = "received %s!";
    private static final String echoTemplate2 = "%s speak to %s \'%s\'";
    private final AtomicLong counter = new AtomicLong();

    //1. Get请求，url传参，返回json。
    //curl http://localhost:8080/echo/getter/pattern1?content=hello
    @RequestMapping(value = "/getter/pattern1", method = RequestMethod.GET)
    public Echo getterPattern1(String content) {
        return new Echo(counter.incrementAndGet(), String.format(echoTemplate1, content));
    }

    //curl http://localhost:8080/echo/getter/pattern2?content=hello
    @RequestMapping(value = "/getter/pattern2", method = RequestMethod.GET)
    public Echo getterPattern2(@RequestParam(value = "content", required = false) String alias) {
        return new Echo(counter.incrementAndGet(), String.format(echoTemplate1, alias));
    }

    //2. Get请求，传递url路径参数，返回json。
    //curl http://localhost:8080/echo/getter/pattern3/123456
    @RequestMapping(value = "/getter/pattern3/{content}", method = RequestMethod.GET)
    public Echo getterPattern3(@PathVariable String content) {
        return new Echo(counter.incrementAndGet(), String.format(echoTemplate1, content));
    }

    //3.Post请求，参数以Http body的途径提交Json数据。
    //curl -i -H "Content-Type:application/json" -d "{\"from\":\"Tom\",\"to\":\"Sandy\",\"content\":\"hello buddy\"}" http://localhost:8080/echo/setter/message1
    @RequestMapping(value = "/setter/message1", method = RequestMethod.POST)
    public Echo setterMessage1(@RequestBody Message message) {
        return new Echo(counter.incrementAndGet(), String.format(echoTemplate2, message.getFrom(), message.getTo(), message.getContent()));
    }

    //4.Post请求，参数以Http body的途径提交表单数据。
    //curl -i -H "Content-Type:application/x-www-form-urlencoded" -d "from=sandy&to=aissen&content=go to" http://localhost:8080/echo/setter/message2
    @RequestMapping(value = "/setter/message2", method = RequestMethod.POST)
    public Echo setterMessage2(@ModelAttribute Message message) {
        return new Echo(counter.incrementAndGet(), String.format(echoTemplate2, message.getFrom(), message.getTo(), message.getContent()));
    }
}
