package com.zhangyu.study;

/**
 * 简单介绍一下 volatile 关键字：
 * 1.先创建多线程，并执行一个run方法，然后在main 方法中将控制循环的变量的值修改为false，但是其他线程
 *    仍然没有跳出循环，这是因为每个线程将类变量读取到线程独立的缓存中，每次循环都是读取缓存值
 *    并不能看到该变量已经被修改了。
 * 2.将该变量加上volatile关键字后，发现线程都跳出了循环停止了执行。
 * 3.说明volatile关键字能强制让线程不读取线程中缓存的值，而是读取内存中的值，这样就能保证每个线程读取到的都是最新的值。
 * 4.特别声明：如果不加volatile关键字，但是在while循环中加一句打印语句时，线程也能跳出循环停止执行
 *    这是因为，打印会让cpu运行停止一下，可能会造成线程把缓存清除掉了，从而读取了内存中的新值，然后跳出了循环。
 */
public class VolatileExample {
    public static void main(String[] args){
        MyRunnable myRunnable = new MyRunnable(true);
//        MyRunnable myRunnable2 = new MyRunnable(false);
//        System.out.println(MyRunnable.flag);
//        new Thread(myRunnable,"线程1").start();
//        new Thread(myRunnable2,"线程2").start();
        for (int i =0;i<10;i++){
            new Thread(myRunnable).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MyRunnable.flag=false;
    }
}

class MyRunnable implements Runnable{
        public static volatile boolean flag = true;             //如果flag变量不加volatile修饰的话，则main方法修改为false值永远都不会被其他线程看到，就会无限循环下去。反之加上后，修改的值会被其他线程读到，则所有线程都会跳出循环。
        private  int count = 0;
//      private AtomicInteger count = new AtomicInteger(0);


    public MyRunnable(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
//        System.out.println(count);
//        count = new AtomicInteger(2);
        System.out.println(Thread.currentThread().getName()+"------------");
        while (flag){
            count++;
//            System.out.println(Thread.currentThread().getName()+"------------");
        }

            System.out.println(count);
    }
}