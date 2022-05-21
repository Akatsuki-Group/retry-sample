package com.quickretry.guava;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author tian
 * @date 2021年05月14日 17:06
 */
@Slf4j
@Service
public class RetryerBuilderDemo {

    public Object call() throws ExecutionException, RetryException {
        //定义重试机制
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()

                //retryIf 重试条件
                .retryIfException()
                .retryIfRuntimeException()
                .retryIfExceptionOfType(Exception.class)
                .retryIfException(throwable -> Objects.equals(throwable, new Exception()))
                .retryIfResult(aBoolean -> Objects.equals(aBoolean, false))

                //等待策略：每次请求间隔1s
                //.withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                .withWaitStrategy(new AlipayWaitStrategy())
                //停止策略 : 尝试请求6次
                .withStopStrategy(StopStrategies.stopAfterAttempt(6))

                //时间限制 : 某次请求不得超过2s , 类似: TimeLimiter timeLimiter = new SimpleTimeLimiter();
                //.withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(2, TimeUnit.SECONDS))

                //默认的阻塞策略：线程睡眠
                //.withBlockStrategy(BlockStrategies.threadSleepStrategy())
                //自定义阻塞策略：自旋锁
                //.withBlockStrategy(new SpinBlockStrategy())

                //自定义重试监听器
                .withRetryListener(new RetryLogListener())

                .build();

        //定义请求实现
        Callable<Boolean> callable = new Callable<Boolean>() {
            int times = 1;

            @Override
            public Boolean call() throws Exception {
                log.info("call times={}", times);
                System.out.println("call times= "+times);
                times++;

                if (times == 2) {
                    throw new NullPointerException();
                } else if (times == 3) {
                    throw new Exception();
                } else if (times == 4) {
                    throw new RuntimeException();
                } else if (times == 5) {
                    return false;
                } else {
                    return true;
                }

            }
        };

        //利用重试器调用请求
        return retryer.call(callable);
    }
}
