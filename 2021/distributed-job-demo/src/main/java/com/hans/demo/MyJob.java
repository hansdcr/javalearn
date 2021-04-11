package com.hans.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyJob {
    @Autowired
    private Process process;

    /**
     * initialDelay = 5000 延迟启动，延迟时间5秒
     * fixedDelayString = "3000"  每3秒执行一次
     * cron  定时任务规则  https://cron.qqe2.com/
     *
     */
//    @Scheduled(fixedDelayString = "3000", initialDelay = 5000)
//    public void process() throws InterruptedException {
//        log.info("---------start");
//        log.info("processing .....");
//        Thread.sleep(5000);
//        log.info("---------stop");
//    }

//    @Scheduled(cron = "0,5 * * * * ?")
//    public void process2(){
//        log.info("-----------start.......");
//    }

//    @Scheduled(fixedRate = 2000)
//    public void process3() throws InterruptedException {
//        log.info("process3 -------- start");
//        Thread.sleep(3000);
//        log.info("process3 -------- stop");
//    }
//
//    @Scheduled(fixedRate = 2000)
//    public void process4() throws InterruptedException {
//        log.info("process4 -------- start");
//        Thread.sleep(3000);
//        log.info("process4 -------- stop");
//    }

    /**
     *  异步的方式执行任务，确保按照每2秒的频率执行任务
     * @throws InterruptedException
     */
    @Scheduled(fixedRate = 2000)
    public void process5() throws InterruptedException {
        log.info("process5 -------- start");
        process.doProcess();
        log.info("process5 -------- stop");
    }
}
