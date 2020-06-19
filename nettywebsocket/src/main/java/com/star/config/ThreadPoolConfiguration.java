package com.star.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfiguration {
    @Bean("threadPool")
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(1);
    }
}
