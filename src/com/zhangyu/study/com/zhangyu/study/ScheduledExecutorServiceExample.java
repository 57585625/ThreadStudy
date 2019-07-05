package com.zhangyu.study;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/***
 * ScheduledExecutorService：Java 调度池，主要有两种用法：
 * 1.延迟执行调度任务，具体用法自行百度。
 * 2.定时循环执行调度任务，效果如下main方法。
 * initialDelay:开始执行的间隔时间。
 * period：循环执行任务的间隔时间。
 */
public class ScheduledExecutorServiceExample {
    
    public static void main(String[] args){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("经验+1");
            }
        },10,1, TimeUnit.SECONDS);
    }
}
