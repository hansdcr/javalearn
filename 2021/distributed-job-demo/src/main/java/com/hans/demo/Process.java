package com.hans.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Process {

    @Async
    public void doProcess(){
        log.info(".......doProcess");
    }
}
