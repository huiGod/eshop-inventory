package com.huigod.eshop.inventory.controller;

import com.huigod.eshop.inventory.model.ProductInventory;
import com.huigod.eshop.inventory.request.ProductInventoryCacheReloadRequest;
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
    ProductInventory productInventory;
    try {
      Request request = new ProductInventoryCacheReloadRequest(productId,
          productInventoryService);
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
        return productInventory;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return new ProductInventory(productId, -1L);
  }

}
