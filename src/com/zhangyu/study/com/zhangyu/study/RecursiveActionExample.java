package com.zhangyu.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/***
 * ForkJoinPool 线程池可实现将线程的主任务分成多个子任务来执行，有两个抽象类可继承：
 * 一种是无返回值的类，不需要合并结果集（RecursiveAction）。
 * 一种是有返回值的类，需要合并结果集。
 * MyRecursiveAction 例子中，在成员变量定义一个阈值，在if语句值判断如果还没到阈值点，则继续分配子任务
 * 并将创建的子任务放到 invokeAll（）中，当到达阈值时，则不再创建子任务，走else，来执行任务，注意：只有没有子任务的子任务才执行，
 * 本案例最后产生16个没有子任务的子任务，所以打印16次else中的语句。
 */
public class RecursiveActionExample {
    
    public static void main(String[] args){
        ForkJoinPool pool = new ForkJoinPool(4);  //构造参数代表cpu核心数，不是最大线程数
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(240);
        pool.invoke(myRecursiveAction);
        pool.shutdown();
    }
}

class MyRecursiveAction  extends RecursiveAction{
    long workLoad = 0;

    public MyRecursiveAction(long workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        if(this.workLoad>16){
//            System.out.println(workLoad);
            List<MyRecursiveAction> all = new ArrayList<>();
            all.addAll(create());     //当阈值大于16时，则将当前任务分成两个子任务
            invokeAll(all);
        }else{
            System.out.println(Thread.currentThread().getName()+"-----"+ workLoad);
        }
    }

    public List<MyRecursiveAction> create(){
        MyRecursiveAction myRecursiveAction2 = new MyRecursiveAction(this.workLoad/2);
        MyRecursiveAction myRecursiveAction3 = new MyRecursiveAction(this.workLoad/2);
        List<MyRecursiveAction> list = new ArrayList<>();
        list.add(myRecursiveAction2);
        list.add(myRecursiveAction3);
        return list;
    }
}
