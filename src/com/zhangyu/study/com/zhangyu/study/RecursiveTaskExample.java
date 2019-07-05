package com.zhangyu.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask 是带返回值的任务，需要对每个子任务调用join方法来获取返回值
 * 再对返回值进行汇总。
 * 本案例 是求1加到99的和，但每个执行的子任务最多只能求10个数的和。
 */
public class RecursiveTaskExample {

    public static void main(String[] args){
        ForkJoinPool pool = new ForkJoinPool();
        MyRecursiveTask task = new MyRecursiveTask(1, 100);
        int sum = (int)pool.invoke(task);
        System.out.println(sum);
        pool.shutdown();
    }
}

class MyRecursiveTask extends RecursiveTask{
    private static final int MAXNUM = 10;           //定义常量，要求每个任务最多只能统计十个数
    private  int start ;
    private  int end ;

    public MyRecursiveTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Object compute() {
        if(end-start<=this.MAXNUM){

            int sum = 0;
            for (int i=start;i<end;i++){
                sum += i;               // 对每个需要执行任务的子任务的数据进行求和。
            }
//            System.out.println(sum);
            return sum;
        }
        List<MyRecursiveTask> myRecursiveActions = create();
        invokeAll(myRecursiveActions);
        int res = 0;
        for (RecursiveTask task: myRecursiveActions) {
            res += (int) task.join();           //将每个if中统计的结果sum再进行求和，即算出1加到99的总和
//            System.out.println(res);
        }
//        System.out.println(res);
        return res;                            //返回结果，为main方法中的返回的总结果
    }
    public List<MyRecursiveTask> create(){
        int mid = (start+end)/2;
        MyRecursiveTask myRecursiveTask2 = new MyRecursiveTask(start,mid);
        MyRecursiveTask myRecursiveTask3 = new MyRecursiveTask(mid,end);
        List<MyRecursiveTask> list = new ArrayList<>();
        list.add(myRecursiveTask2);
        list.add(myRecursiveTask3);
        return list;
    }
}
