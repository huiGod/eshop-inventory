package com.huigod.eshop.inventory.threadpool;

import com.huigod.eshop.inventory.request.Request;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class RequestProcessorThread implements Callable<Boolean> {

  private ArrayBlockingQueue<Request> queue;

  public RequestProcessorThread(ArrayBlockingQueue queue) {
    this.queue = queue;
  }

  @Override
  public Boolean call() throws Exception {
    try {
      while (true) {
        //ArrayBlockingQueue will be blocked when queue is empty or full
        Request request = queue.take();
        boolean forceRefresh = request.isForceRefresh();


      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }
}
