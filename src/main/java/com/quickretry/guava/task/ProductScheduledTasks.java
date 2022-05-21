package com.quickretry.guava.task;

import com.github.rholder.retry.Retryer;
import com.quickretry.guava.retryer.ProductRetryerBuilder;
import com.quickretry.guava.service.ProductInformationHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductScheduledTasks {

    @Autowired
    private ProductRetryerBuilder builder;

    @Autowired
    private ProductInformationHander hander;

    /**
     * 同步商品价格定时任务
     * @Scheduled(fixedDelay = 30000) ：上一次执行完毕时间点之后30秒再执行
     */
    //@Scheduled(fixedDelay = 30*1000)
    public void syncPrice() throws Exception{
        Retryer retryer=builder.build();
        retryer.call(hander);
    }

}