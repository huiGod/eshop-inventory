package com.huigod.eshop.inventory.service.impl;

import com.huigod.eshop.inventory.request.Request;
import com.huigod.eshop.inventory.request.RequestQueue;
import com.huigod.eshop.inventory.service.RequestAsyncProcessService;
import java.util.concurrent.ArrayBlockingQueue;
import org.springframework.stereotype.Service;

@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

  @Override
  public void process(Request request) {

    try {
      //route to queue by productId
      ArrayBlockingQueue<Request> queue = getRoutingQuequ(request.getProductId());
      //put request to queue
      queue.put(request);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * @Author: huiGod
   * @Description: route to exact queue by hash key
   * @Date: 8:40 PM 30/10/2017
   */
  private ArrayBlockingQueue<Request> getRoutingQuequ(Integer productId) {
    RequestQueue requestQueue = RequestQueue.getInstance();

    String key = String.valueOf(productId);

    int h;
    int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

    int index = (requestQueue.queueSize() - 1) & hash;
    System.out.println("===========日志===========:路由内存队列，商品id=" + productId + ",队列索引：" + index);
    return requestQueue.getQueue(index);
  }


}
