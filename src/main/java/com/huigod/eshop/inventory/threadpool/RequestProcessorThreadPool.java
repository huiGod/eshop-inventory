package com.huigod.eshop.inventory.threadpool;

import com.huigod.eshop.inventory.request.Request;
import com.huigod.eshop.inventory.request.RequestQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestProcessorThreadPool {

  //线程池，可以通过配置文件来设置
  private ExecutorService threadPool = Executors.newFixedThreadPool(10);

  public RequestProcessorThreadPool() {
    RequestQueue requestQueue = RequestQueue.getInstance();
    for (int i = 0; i < 10; i++) {
      ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<>(100);
      requestQueue.addQueue(queue);
      threadPool.submit(new RequestProcessorThread(queue));
    }
  }

  /**
   * @Author: huiGod
   * @Description: 使用内部类的方式实现单例模式
   * @Date: 6:01 PM 29/10/2017
   */
  private static class Singleton {

    private static RequestProcessorThreadPool instance;

    static {
      instance = new RequestProcessorThreadPool();
    }


    public static RequestProcessorThreadPool getInstance() {
      return instance;
    }

  }

  /**
   * @Author: huiGod
   * @Description: 由于jvm的机制，不管多少个线程并发初始化，内部类的初始化一定只会发生一次
   * @Date: 6:02 PM 29/10/2017
   */
  public static RequestProcessorThreadPool getInstance() {
    return Singleton.getInstance();
  }

  /**
   * @Author: huiGod
   * @Description: 初始化的便捷方法
   * @Date: 6:04 PM 29/10/2017
   */
  public static void init() {
    getInstance();
  }

}
