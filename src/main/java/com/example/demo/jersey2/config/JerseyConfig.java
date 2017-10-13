package com.example.demo.jersey2.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/10/13.
 */
@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        //jersey配置(有两种注册方式，注册类，注册包):
        //register(Demo.class);
        //构造函数，在这里注册需要使用的内容，（过滤器，拦截器，API等）
        //项目打为jar包启动时，不能使用包注册的方式，否则会报FileNotFound异常。
        packages("com.example.demo.jersey2");
    }
}
