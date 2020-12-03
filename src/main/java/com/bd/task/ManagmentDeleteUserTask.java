package com.bd.task;

import com.bd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 管理已被删除的用户信息任务
 */

@Component
@Slf4j
public class ManagmentDeleteUserTask {

    @Resource
    private UserService userService;

    /**
     * 每月1、15号凌晨4点整销毁一次，已被删除用户的密码信息。
     */
    @Async("destoryDelUserPwdTaskExecutor")
    // @Scheduled(cron = "0 0 4 1,15 * * *")           // 每月1、15号凌晨4点执行一次
    // @Scheduled(cron = "0 5 * * * ? ")
    public void destoryInvalidUserPwd(){
        log.info("销毁被删除用户的密码信息===begin");
        userService.destoryInvalidUserPwdByProce();
        log.info("销毁被删除用户的密码信息===end");
    }
}
