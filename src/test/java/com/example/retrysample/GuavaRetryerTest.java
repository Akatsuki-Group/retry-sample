package com.example.retrysample;

import com.github.rholder.retry.RetryException;
import com.quickretry.DemoRetryerApplication;
import com.quickretry.guava.RetryerBuilderDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;

/**
 * @author tian
 * @date 2021年05月14日 17:12
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DemoRetryerApplication.class})
public class GuavaRetryerTest {
    @Autowired
    private RetryerBuilderDemo retryDemo;
    @Test
    public void testGuavaRetryer() throws ExecutionException, RetryException {
        for (int i = 0; i < 3; i++) {
            System.out.println("--------------");
            retryDemo.call();
        }
    }
}
