package com.example.demo;

import com.example.demo.jersey2.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */


public class HttpClientTest {
    //简单的模拟一下发送HttpPost请求
    @Test
    public void testPostMethod() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post对象
        HttpPost httpPost = new HttpPost("http://localhost:8080/user/new");

        User user = new User("zzh", 1);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writeValueAsString(user);
        StringEntity entity = new StringEntity(jsonObject, ContentType.APPLICATION_JSON);
        //设置请求的内容
        httpPost.setEntity(entity);
        //设置请求头
        httpPost.setHeader("username", "zzh2");

        //执行post请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String string = EntityUtils.toString(response.getEntity());
        System.out.println(string);
        response.close();
        httpClient.close();

    }

    @Test
    public void testGetMethod() throws URISyntaxException, IOException {
        //构建一个httpclient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //构建一个uri对象
        URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/user/1");
        uriBuilder.addParameter("test", "haha");
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行请求
        CloseableHttpResponse httpResponse = httpClient.execute(get);
        HttpEntity entity = httpResponse.getEntity();
        System.out.println(EntityUtils.toString(entity, "utf-8"));
        //获取响应结果
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);

        //关闭httpclient
        httpResponse.close();
        httpClient.close();
    }


}
