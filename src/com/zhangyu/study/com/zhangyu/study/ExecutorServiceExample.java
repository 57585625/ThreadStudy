package com.zhangyu.study;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService demo：执行服务器用于异步执行代码，一个线程一旦开启一个异步线程来执行任务，异步线程再后台运行
 * 不会影响主线程的执行，互补影响，创建执行服务器相当于创建一个线程池，可以指定线程个数。
 * 执行异步程序的方法主要有：如下，具体区别，请百度查询
 * execute(Runnable)
 * submit(Runnable)
 * submit(Callable)
 * invokeAny(Collection)
 * invokeAll(Collection)
 * 调用完异步程序后，必须关闭线程，调用shutdown方法。
 * 为什么一定要关闭执行服务器？使用完 ExecutorService 之后，应该将其关闭，以使其中的线程不再允许。
 * 比如，如果你的应用是通过一个 main 方法启动的，之后 main 方法退出了你的应用，如果你的应用有一个活动的 ExecutorService，它将还会保持运行。
 * ExecutorService 里的活动线程阻止了 JVM 的关闭。要终止 ExecutorService 里的线程，你需要调用 ExecutorService 的 shutdown 方法。
 * ExecutorService 并不会立即关闭，但它将不再接受新的任务，而且一旦所有线程都完成了当前任务的时候，ExecutorService 将会关闭。在 shutdown 被调用之前所有提交给ExecutorService 的任务都被执行。
 * 如果你想立即关闭 ExecutorService，你可以调用 shutdownNow 方法，这样会立即尝试停止所有执行中的任务，并忽略掉那些已提交但尚未开始处理的任务。无法保证执行任务的正确执行。可能它们被停止了，也可能已经执行结束。
 */
public class ExecutorServiceExample {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        MyTestRunnable myRunnable = new MyTestRunnable(executorService);
        new Thread(myRunnable,"线程1").start();
        new Thread(myRunnable,"线程2").start();
        new Thread(myRunnable,"线程3").start();
    }
}

class MyTestRunnable implements Runnable{
    int count = 3;
    ExecutorService executorService;

    public MyTestRunnable(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"开始执行。。。");
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                count --;             // 用count来记录异步线程执行次数，当所有线程都执行完异步程序后，调用shutdown方法关闭线程池
                if(count ==0){
                    executorService.shutdown();
                }
                System.out.println(Thread.currentThread().getName()+"异步执行。。。");
            }
        });
        System.out.println(Thread.currentThread().getName()+"执行结束。。。");
    }
}
