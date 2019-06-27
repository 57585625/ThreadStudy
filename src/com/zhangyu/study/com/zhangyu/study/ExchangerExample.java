package com.zhangyu.study;

import java.util.concurrent.Exchanger;

/**
 * 交换机demo：交换只能两个线程间进行元素交换，率先到达exchange的两个线程相互之间进行交换。
 */
public class ExchangerExample {
    
    public static void main(String[] args){
        Exchanger exchanger = new Exchanger();
        ExchangerRunnable exchangerRunnable = new ExchangerRunnable(exchanger,"线程1数据.。。");
        ExchangerRunnable exchangerRunnable2 = new ExchangerRunnable(exchanger,"线程2数据.。。");
        ExchangerRunnable exchangerRunnable3 = new ExchangerRunnable(exchanger,"线程3数据.。。");
        new Thread(exchangerRunnable,"线程1").start();
        new Thread(exchangerRunnable2,"线程2").start();
        new Thread(exchangerRunnable3,"线程3").start();
    }
}

class ExchangerRunnable implements  Runnable{
    Exchanger exchanger;
    String str;

    public ExchangerRunnable(Exchanger exchanger, String str) {
        this.exchanger = exchanger;
        this.str = str;
    }

    @Override
    public void run() {
        try {
            String old = this.str;
            String newStr = (String) exchanger.exchange(this.str);
            System.out.println(Thread.currentThread().getName()+"的旧数据："+old+"--新数据："+newStr);
            System.out.println("end..........");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}