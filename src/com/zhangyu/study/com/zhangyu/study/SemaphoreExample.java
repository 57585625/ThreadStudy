package com.zhangyu.study;

import java.util.concurrent.Semaphore;

/**
 * 信号量demo：简单理解：信号量相当于一个许可证书，可自定义证书数量，当一个线程获取证书许可证是才能向下
 *  执行，执行完毕后释放信号到信号池。
 *  用途：可用于重要代码块同时只能被N个线程访问，其他线程阻塞，直到有信号被释放出来。
 */
public class SemaphoreExample {
    public static void main(String[] args){
        Semaphore semaphore = new Semaphore(3);
        SemaphoreRunnable semaphoreRunnable = new SemaphoreRunnable(semaphore);
        new Thread(semaphoreRunnable,"线程1").start();
        new Thread(semaphoreRunnable,"线程2").start();
        new Thread(semaphoreRunnable,"线程3").start();
        new Thread(semaphoreRunnable,"线程4").start();
        new Thread(semaphoreRunnable,"线程5").start();
        new Thread(semaphoreRunnable,"线程6").start();
    }

}

class SemaphoreRunnable implements  Runnable{
    Semaphore semaphore;

    public SemaphoreRunnable(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire(); //获取信号
            System.out.println(Thread.currentThread().getName()+"--获取信号许可");
            System.out.println("当前剩余信号量："+semaphore.availablePermits());
            Thread.sleep(3000);
            semaphore.release(); //释放信号
            System.out.println(Thread.currentThread().getName()+"--释放信号");
            System.out.println("当前剩余信号量："+semaphore.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}