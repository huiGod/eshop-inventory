package com.huigod.eshop.inventory.threadpool;

import com.huigod.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.huigod.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.huigod.eshop.inventory.request.Request;
import com.huigod.eshop.inventory.request.RequestQueue;
import java.util.Map;
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

        if (!forceRefresh) {
          //filter multiple read request
          RequestQueue requestQueue = RequestQueue.getInstance();
          Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();

          if (request instanceof ProductInventoryDBUpdateRequest) {
            //set productId flag to true when request is update
            flagMap.put(request.getProductId(), true);
          } else if (request instanceof ProductInventoryCacheRefreshRequest) {
            //when request is refresh
            Boolean flag = flagMap.get(request.getProductId());
            //if flag is not null and is true,meaning there is a update request in queue before
            //Maybe no data in redis but exists value in db
            if (flag == null) {
              flagMap.put(request.getProductId(), false);
            }
            if (flag != null && flag) {
              flagMap.put(request.getProductId(), false);
            }
            //if flag is not null and is false,meaning there are a update request and a refresh request
            if (flag != null && !flag) {
              //filter this request and not putting in queue
              return true;
            }
          }
        }
        System.out.println("===========日志===========:工作线程处理请求，商品id=" + request.getProductId());
        request.process();

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }
}
