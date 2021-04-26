package com.example.distributedlimiter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimiterController {
    @GetMapping("/guava")
    public void guava(){

    }
}
