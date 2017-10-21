package com.example.demo.jersey2.async.controller;

import com.example.demo.jersey2.async.domain.Book;
import com.example.demo.jersey2.async.domain.Books;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by Administrator on 2017/10/21.
 */

/**
 * TODO 还有问题
 */
@Component
@Path("books")
@Produces({MediaType.APPLICATION_JSON})
public class AsyncResource {
    public static final long TIMEOUT = 120;
    final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @GET
    public void getAll(@Suspended final AsyncResponse asyncResponse) {
        configResponse(asyncResponse);
        final BatchRunner batchTask = new BatchRunner(asyncResponse);
        threadPool.submit(batchTask);
    }

    private void configResponse(final AsyncResponse asyncResponse) {
        asyncResponse.register(new CompletionCallback() {
            @Override
            public void onComplete(Throwable throwable) {
                if (throwable == null) {
                    System.out.println("CompletionCallback-onComplete: OK");
                } else {
                    System.out.println("CompletionCallback-onComplete: ERROR: " + throwable.getMessage());
                }
            }
        });

        asyncResponse.register(new ConnectionCallback() {

            @Override
            public void onDisconnect(AsyncResponse asyncResponse) {
                //Status.GONE=410
                System.out.println("ConnectionCallback-onDisconnect");
                asyncResponse.resume(Response.status(Response.Status.GONE).entity("disconnect!").build());
            }
        });

        asyncResponse.setTimeoutHandler(new TimeoutHandler() {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                //Status.SERVICE_UNAVAILABLE=503
                System.out.println("TIMEOUT");
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(TIMEOUT, TimeUnit.SECONDS);
    }

    class BatchRunner implements Runnable {
        private final AsyncResponse response;

        public BatchRunner(AsyncResponse asyncResponse) {
            this.response = asyncResponse;
        }

        @Override
        public void run() {
            try {
                Books books = doBatch();
                response.resume(books);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        private Books doBatch() throws InterruptedException {
            Books books = new Books();
            for (int i = 0; i < 10; i++) {
                Thread.sleep(500);
                Book book = new Book(i + 10000l, "Java RESTful Web Services", "华章");
                System.out.println(book);
                books.getBookList().add(book);
            }
            return books;
        }
    }
}
