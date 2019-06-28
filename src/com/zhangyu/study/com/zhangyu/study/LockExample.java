package com.zhangyu.study;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {

    public static void main(String[] args){
        LockRunnble lockRunnble = new LockRunnble();
        new Thread(lockRunnble,"线程1").start();
        new Thread(lockRunnble,"线程2").start();
        new Thread(lockRunnble,"线程3").start();
        new Thread(lockRunnble,"线程4").start();
        new Thread(lockRunnble,"线程5").start();
    }
}

class LockRunnble implements  Runnable{
    int count = 0;
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        lock.lock();
        count ++;
        System.out.println(Thread.currentThread().getName()+"=====进来了！---"+count);
        lock.unlock();
    }
}
