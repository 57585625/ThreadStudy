package com.zhangyu.study;

public class Demo1 {
    public static void main(String[] args){
        MyRunnable myRunnable = new MyRunnable();
        for (int i =0;i<10;i++){
            new Thread(myRunnable).start();
        }
    }
}

class MyRunnable implements Runnable{
    private  volatile int count = 0;
    @Override
    public void run() {
        System.out.println(count);
        count = 2;
        System.out.println(count);
    }
}