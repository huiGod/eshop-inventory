package com.huigod.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class RequestQueue {

  //memory queue
  private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

  //flag map
  private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();


  /**
   * @Author: huiGod
   * @Description: Singleton pattern with inner class load
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

  /**
   * @Date: 11:12 PM 06/11/2017
   * @Author: huiGod
   * @Description: get flag map
   */
  public Map<Integer, Boolean> getFlagMap() {
    return flagMap;
  }
}
