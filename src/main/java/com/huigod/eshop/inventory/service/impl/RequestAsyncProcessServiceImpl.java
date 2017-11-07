package com.huigod.eshop.inventory.service.impl;

import com.huigod.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.huigod.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.huigod.eshop.inventory.request.Request;
import com.huigod.eshop.inventory.request.RequestQueue;
import com.huigod.eshop.inventory.service.RequestAsyncProcessService;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import org.springframework.stereotype.Service;

@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

  @Override
  public void process(Request request) {

    try {
      //filter multiple read request
      RequestQueue requestQueue = RequestQueue.getInstance();
      Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();

      if (request instanceof ProductInventoryDBUpdateRequest) {
        //set productId flag to true when request is update
        flagMap.put(request.getProductId(), true);
      } else if (request instanceof ProductInventoryCacheRefreshRequest) {
        //when request is refresh
        //if flag is not null and is true,meaning there is a update request in queue before
        Boolean flag = flagMap.get(request.getProductId());
        if (flag != null && flag) {
          flagMap.put(request.getProductId(), false);
        }
        //if flag is not null and is false,meaning there are a update request and a refresh request
        if (flag != null && !flag) {
          //filter this request and not putting in queue
          return;
        }
      }
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
    return requestQueue.getQueue(index);
  }


}
