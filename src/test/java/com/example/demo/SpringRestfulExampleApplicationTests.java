package com.example.demo;

import com.example.demo.jersey2.user.domain.User;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;


//要先启动主类，然后再启动Junit的测试类
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =SpringRestfulExampleApplication.class )
//@SpringBootTest(classes = SpringRestfulExampleApplication.class)

public class SpringRestfulExampleApplicationTests {

	private RestTemplate restTemplate = new RestTemplate();

	//方式一：SimpleClientHttpRequestFactory
	//缺点 不能设置header，代理，认证等配置
	@Test
	public void simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(1000);
		requestFactory.setReadTimeout(1000);
		RestTemplate restTemplate1 = new RestTemplate(requestFactory);
		String response=restTemplate1.getForObject("http://localhost:8080/user/zzh",String.class);
		System.out.println(response);
/*		System.out.println(response.getUserId());
		System.out.println(response.getUserName());*/
	}

	/**
	 * 发送一个get请求，并接受封装成ResponseEntity
	 */
	@Test
	public void testHeader() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("username", "zzh");
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity httpEntity  = new HttpEntity(httpHeaders);
		ResponseEntity response=restTemplate.exchange("http://localhost:8080/user/header",HttpMethod.GET,httpEntity,String.class);
		System.out.println(response.getBody());
		System.out.println(response.getStatusCode());
		System.out.println(response.getHeaders().get("xxx").get(0));
	}

	@Test
	public void getString()
	{
		String response = restTemplate.getForObject("http://localhost:8080/user/string?name=zzh", String.class);
		System.out.println(response);
	}

	@Test
	public void getObject()
	{
		User response = restTemplate.getForObject("http://localhost:8080/user/1", User.class);
		System.out.println(response);
		System.out.println(response.getUserId());
		System.out.println(response.getUserName());
	}

	@Test
	public void getMap()
	{
		Map response = restTemplate.getForObject("http://localhost:8080/user/1", Map.class);
		System.out.println(response);
		System.out.println(response.get("userName"));
		System.out.println(response.get("userId"));
	}

	@Test
	public void postWithBody(){
		//1.设置header

		//2.设置参数
		Map<String, String> map = new LinkedMultiValueMap();
		User user = new User("zzh",1);
		HttpEntity httpEntity = new HttpEntity(user);
		//3.执行请求
		ResponseEntity responseEntity = restTemplate.exchange("http://localhost:8080/user/new", HttpMethod.POST,httpEntity, User.class);
		//4.获取返回结果
		System.out.println(responseEntity.getBody().toString());
	}

	@Test
	public void postWithQueryParam(){
		String response = restTemplate.getForObject("http://localhost:8080/user/user?id=1", String.class);
		System.out.println(response);
	}

	@Test
	public void postWithQueryParam1(){
		ResponseEntity responseEntity = restTemplate.exchange("http://localhost:8080/user/user?id={id}", HttpMethod.GET, null,String.class, 1);
		System.out.println(responseEntity.getBody());
	}

	@Test
	public void getWithQueryParam2()
	{
		MultiValueMap<String, String> map = new LinkedMultiValueMap();
		map.add("name", "zzh");
		ResponseEntity responseEntity = restTemplate.exchange("http://localhost:8080/user/string?name={name}", HttpMethod.GET, null, String.class, map);
		System.out.println(responseEntity.getBody());
	}

	//RestTemplate 查询参数有特殊字符
	@Test
	public void getWithQuerySpecialParam()
	{
		String url = "http://localhost:8080/user/user?id=1";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		builder.queryParam("test", "haha");
		URI uri = builder.build().encode().toUri();
		System.out.println(uri.toString());
		ResponseEntity responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
		System.out.println(responseEntity.getBody());
	}

	/**
	 * GET请求,要返回一些复合数据类型时的处理
	 * (1)返回List类型数据

	 DicData[] dicResult = restTemplate.getForObject( Constants.SERVER_URL + "/dicDatas/dicData?"
	 + "group={group}", DicData[].class, group);
	 List<DicData> list = Arrays.asList(dicResult);

	 或者
	 // pass generic information to resttemplate; ParameterizedTypeReference为spring3.2版本后引进的类
	 ParameterizedTypeReference<List<DicData>> responseType = new ParameterizedTypeReference<List<DicData>>();
	 ResponseEntity<List<DicData>> resp = restTemplate.exchange(Constants.SERVER_URL + "/dicDatas/dicData?group={group}",
	 HttpMethod.GET, null, responseType);
	 List<DicData> list = resp.getBody();
	 (2)返回属性中有范型数据的复合对象
	 比如，分页对象
	 ResponseEntity<String> results = restTemplate.exchange(url,HttpMethod.GET, null, String.class, params);
	 // 借助com.fasterxml.jackson.databind.ObjectMapper 对象来解析嵌套的json字符串
	 ObjectMapper mapper = new ObjectMapper(); mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	 PageInfo<Product> page = mapper.readValue(results.getBody(), new TypeReference<PageInfo<Product>>() { });
	 */

	//Spring RestTemplate and Proxy Auth
	@Test
	public void  restTemplateWithProxyAuth()  throws Exception
	{
		final String username = "myusername";
		final String password = "myPass";
		final String proxyUrl = "proxy.nyc.bigtower.com";
		final int port = 8080;

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				new AuthScope(proxyUrl, port),
				new UsernamePasswordCredentials(username, password));

		HttpHost myProxy = new HttpHost(proxyUrl, port);
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();

		clientBuilder.setProxy(myProxy).setDefaultCredentialsProvider(credsProvider).disableCookieManagement();

		HttpClient httpClient = clientBuilder.build();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setHttpClient(httpClient);

		RestTemplate restTemplate = new RestTemplate(factory);
		System.out.println(restTemplate.toString());
	}
}
