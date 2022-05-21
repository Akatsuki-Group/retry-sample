package com.quickretry.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RetryTemplateTest implements ApplicationRunner {
    @Resource
    private RetryTemplate retryTemplate;

    @Override
    public void run(ApplicationArguments args) {
        try {
            boolean result = retryMethod(RandomUtil.randomNumbers(10));
            log.info("Request Result={}", result);
        } catch (RequestRetryException e) {
            log.error("Request Exception - message:{}", e.getMessage());
        }
    }

    private boolean retryMethod(String requestId) throws RequestRetryException {
        return retryTemplate.execute(context -> {
            log.info("Processing request - Param={} - Retry: count={}", requestId, context.getRetryCount());
            //TODO 业务逻辑处理
            throw new RequestRetryException("Request Retry");
        }, context -> {
            log.info("Recovering request - Param={} - Retry: count={}", requestId, context.getRetryCount());
            //TODO 错误逻辑处理
            return false;
        });
    }

    @Retryable(
            maxAttempts = 5,
            backoff = @Backoff(delay = 1000L, maxDelay = 2000),
            value = {
                    RuntimeException.class
            },
            exclude = IllegalArgumentException.class,
            listeners = "defaultRetryListener"
    )
    public boolean retryMethod1(String requestId) {
        log.info("Processing request: {}", requestId);
        throw new RuntimeException("Failed Request");
    }

    @Recover
    public boolean recover(RuntimeException ex, String requestId) {
        log.error("Recovering request {} - {}", requestId, ex.getMessage());
        return false;
    }
}