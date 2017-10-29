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
    while (true) {
      break;
    }
    return true;
  }
}
