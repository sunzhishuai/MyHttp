package com.reliance.myhttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by sunzhishuai on 17/3/29.
 * E-mail itzhishuaisun@sina.com
 */

public class ThreadPoolManager {


    private static ThreadPoolManager instance = new ThreadPoolManager();
    private final ThreadPoolExecutor threadPoolExecutor;
    /**
     * 阻塞请求队列
     */
    private LinkedBlockingQueue service = new LinkedBlockingQueue();

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    private ThreadPoolManager() {
        // 核心线程数目  最大线程时间  线程存活时间    阻塞缓冲区，  拒绝策略
        threadPoolExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue(4), rejectedExecutionHandler);
        //开启
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 实例一个请求队列
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                FutureTask<Runnable> futureTask = null;
                try {
                    futureTask = (FutureTask<Runnable>) service.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e("error",e.toString());
                }
                if (futureTask != null) {
                    //拿到请求添加
                    threadPoolExecutor.execute(futureTask);
                }
            }

        }
    };

    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                service.put(new FutureTask<Runnable>(r, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("error",e.toString());
            }
        }
    };
    /**
     * 暴露api
     */
    public<T> void execute(FutureTask<T> futureTask){
        try {
            service.put(futureTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("error",e.toString());
        }
    }
}
