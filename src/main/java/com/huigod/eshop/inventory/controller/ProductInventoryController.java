package com.huigod.eshop.inventory.controller;

import com.huigod.eshop.inventory.model.ProductInventory;
import com.huigod.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.huigod.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.huigod.eshop.inventory.request.Request;
import com.huigod.eshop.inventory.service.ProductInventoryService;
import com.huigod.eshop.inventory.service.RequestAsyncProcessService;
import com.huigod.eshop.inventory.vo.Response;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductInventoryController {

  @Resource
  private ProductInventoryService productInventoryService;
  @Resource
  private RequestAsyncProcessService requestAsyncProcessService;

  /**
   * @Date: 9:04 PM 30/10/2017
   * @Author: huiGod
   * @Description: update product inventory
   */
  @RequestMapping("/updateProductInventory")
  @ResponseBody
  public Response updateProductInventory(ProductInventory productInventory) {
    System.out.println(
        "===========日志===========: 接收到更新商品库存的请求，商品id=" + productInventory.getProductId()
            + ", 商品库存数量=" + productInventory.getInventoryCnt());
    Response response;
    try {
      Request request = new ProductInventoryDBUpdateRequest(productInventory,
          productInventoryService);
      requestAsyncProcessService.process(request);
      response = new Response(Response.SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      response = new Response(Response.FAILURE);
    }

    return response;
  }

  @RequestMapping("/getProductInventory")
  @ResponseBody
  public ProductInventory getProductInventory(Integer productId) {
    System.out.println("===========日志===========: 接收到一个商品库存的读请求，商品id=" + productId);
    ProductInventory productInventory;
    try {
      Request request = new ProductInventoryCacheRefreshRequest(productId,
          productInventoryService,false);
      requestAsyncProcessService.process(request);

      //async execute request then wait short time to try to get inventory cache data updated before
      long startTime = System.currentTimeMillis();
      long endTime;
      long waitTime = 0L;

      while (true) {
        //wait time beyond 200ms then to retrieve directly from DB
        if (waitTime > 200) {
          break;
        }
        //try to get data from cache
        productInventory = productInventoryService.getProductInventoryCache(productId);
        if (productInventory != null) {
          System.out.println(
              "===========日志===========: 在200ms内读取到了redis中的库存缓存，商品id=" + productInventory
                  .getProductId() + ", 商品库存数量=" + productInventory.getInventoryCnt());
          return productInventory;
        } else {
          Thread.sleep(20);
          endTime = System.currentTimeMillis();
          waitTime = endTime - startTime;
        }
      }

      //retrieve data from DB
      productInventory = productInventoryService.findProductInventory(productId);
      if (productInventory != null) {
        //refresh cache
        request = new ProductInventoryCacheRefreshRequest(
            productId, productInventoryService, true);
        requestAsyncProcessService.process(request);
        return productInventory;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return new ProductInventory(productId, -1L);
  }

}
