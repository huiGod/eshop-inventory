package com.huigod.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class RequestQueue {

  //内存队列
  private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();


  /**
   * @Author: huiGod
   * @Description: 使用内部类的方式实现单例模式
   * @Date: 6:01 PM 29/10/2017
   */
  private static class Singleton {

    private static RequestQueue instance;

    static {
      instance = new RequestQueue();
    }


    public static RequestQueue getInstance() {
      return instance;
    }

  }

  /**
   * @Author: huiGod
   * @Description: 由于jvm的机制，不管多少个线程并发初始化，内部类的初始化一定只会发生一次
   * @Date: 6:02 PM 29/10/2017
   */
  public static RequestQueue getInstance() {
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

  /**
   * @Author: huiGod
   * @Description: 添加一个内存队列
   * @Date: 8:23 PM 29/10/2017
   */
  public void addQueue(ArrayBlockingQueue queue) {
    this.queues.add(queue);
  }

  /**
   * @Author: huiGod
   * @Description: return queue size
   * @Date: 8:36 PM 30/10/2017
   */
  public int queueSize() {
    return queues.size();
  }

  /**
   * @Author: huiGod
   * @Description: get queue by index
   * @Date: 8:38 PM 30/10/2017
   */
  public ArrayBlockingQueue<Request> getQueue(int index) {
    return queues.get(index);
  }
}
