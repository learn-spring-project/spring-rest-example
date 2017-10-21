package com.example.demo;

import com.example.demo.jersey2.async.controller.AsyncResource;
import com.example.demo.jersey2.async.domain.Books;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/10/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringRestfulExampleApplication.class)
public class AsyncTest {


    @Test
    public void testAsync() throws InterruptedException, ExecutionException {
        final Invocation.Builder request = target("http://localhost:" + 8080 + "/books").request();
        final AsyncInvoker async = request.async();
        final Future<Books> responseFuture = async.get(Books.class);
        long beginTime = System.currentTimeMillis();
        try {
            Books result = responseFuture.get(AsyncResource.TIMEOUT + 1, TimeUnit.SECONDS);
            System.out.println("Testing result size = {}" + result.getBookList().size());
        } catch (TimeoutException e) {
            System.out.println("Fail to request asynchronously "+ e);
        } finally {
            System.out.println("Elapsed time = {}" + (System.currentTimeMillis() - beginTime));
        }
    }

    @Test
    /**
     * 还有问题
     */
    public void testAsyncCallBack() throws InterruptedException, ExecutionException {
        final AsyncInvoker async = target("http://localhost:" + 8080 + "/books").request().async();
        final Future<Books> responseFuture = async.get(new InvocationCallback<Books>() {
            @Override
            public void completed(Books result) {
                System.out.println("On Completed: " + result.getBookList().size());
            }

            @Override
            public void failed(Throwable throwable) {
                System.out.println("On Failed: " + throwable.getMessage());
                throwable.printStackTrace();
            }
        });
        System.out.println("First response time::" + System.currentTimeMillis());
        try {
            responseFuture.get(AsyncResource.TIMEOUT + 1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println(""+e);
        } finally {
            System.out.println("Second response time::" + System.currentTimeMillis());
        }
    }
    
    private WebTarget target(String url) {
        Client client = ClientBuilder.newClient();
        return client.target(url);
    }


}
