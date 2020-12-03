package com.bd.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 但当代码中存在多个定时任务时，定时任务之间是串行执行，这是Spring限制，spring定时任务默认是串行。
 * 所以不建议直接使用@EnableScheduling开启定时任务的调用, 经过测试得知注解方式默认为串行化的；
 * 如果想要多个定时任务并行执行，建议在配置文件中配置任务线程池后开启定时任务的异步调用。
 */
@Configuration
@EnableScheduling       //开启定时任务
@EnableAsync            //开启异步执行
public class ThreadPoolTaskConfig {
    /** 异步任务线程池的参数设置参考
     *  假设场景：每秒的任务数(tasks)为500~1000; 每个任务的执行时长(taskcost)平均为0.1s; 系统允许的最大响应时间(responsetime)为1s。
     *  【corePoolSize应该设置为最小50】tasks*taskcost=500~1000 * 0.1 = 50；
     *  根据二八原则，如果80%的每秒任务数小于800，那么corePoolSize最大可设置为80。
     */
    /** 核心线程数（默认线程数） */
    private static final int corePoolSize = 80;
    /** 最大线程数 */
    private static final int maxPoolSize = 100;
    /** 允许线程空闲时间（单位：默认为秒） */
    private static final int keepAliveTime = 60;
    /** 缓冲队列大小 */
    private static final int queueCapacity = 100;
    /** 线程池名前缀 */
    private static final String threadNamePrefix = "read-server-async-";

    /**
     * 线程池：定时销毁已被删除用户的密码信息
     * @return
     */
    @Bean("destoryDelUserPwdTaskExecutor") // bean的名称，默认为首字母小写的方法名
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);

        // 线程池对拒绝任务的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

}
