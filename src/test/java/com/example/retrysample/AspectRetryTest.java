package com.example.retrysample;

import com.quickretry.DemoRetryerApplication;
import com.quickretry.sample.demo.RetryDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tian on 2017/8/30.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:aop.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DemoRetryerApplication.class})
public class AspectRetryTest {

    @Autowired
    private RetryDemoService retryDemoService;

    @Test
    public void testRetry() throws Exception {
        for (int i = 0; i < 3; i++) {
            int ans = retryDemoService.genBigNum();

            System.out.println("----" + ans + "----");

            retryDemoService.genSmallNum();

            System.out.println("------------------");
        }
    }

}
