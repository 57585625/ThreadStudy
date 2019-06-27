package com.zhangyu.study;

import java.util.concurrent.CyclicBarrier;

/***
 *  栅栏demo ：简单理解：给所有线程前加一个栅栏，只有等最后一个线程到达栅栏前才会继续向下执行
 *  可以自行定义超时时间，当时间超时后则不再等待未到的线程，继续执行
 */
public class CyclicBarrierExample {
    public static void main(String[] args){
       Runnable barrier1Action = new Runnable() {
           @Override
           public void run() {
               System.out.println("BarrierAction 1 executed");
           }
       };
       Runnable barrier2Action = new Runnable() {
           @Override
           public void run() {
               System.out.println("BarrierAction 2 executed");
           }
       };
        CyclicBarrier c1 = new CyclicBarrier(2,barrier1Action);
        CyclicBarrier c2= new CyclicBarrier(2,barrier2Action);
        CyclicBarrierRunnable cyclicBarrierRunnable = new CyclicBarrierRunnable(c1, c2);
        new Thread(cyclicBarrierRunnable,"选手1").start();
        new Thread(cyclicBarrierRunnable,"选手2").start();
    }
}

class CyclicBarrierRunnable implements  Runnable{
    CyclicBarrier c1;
    CyclicBarrier c2;

    public CyclicBarrierRunnable(CyclicBarrier c1, CyclicBarrier c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() +
                    " waiting at barrier 1");
            System.out.println(Thread.currentThread().getName()+"已准备就绪。。。");
            this.c1.await();
            this.c2.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}