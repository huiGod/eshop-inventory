package com.huigod.eshop.inventory.request;


import com.huigod.eshop.inventory.model.ProductInventory;
import com.huigod.eshop.inventory.service.ProductInventoryService;

/**
 * @Author: huiGod
 * @Description: reload product inventory cache
 * @Date: 9:19 PM 29/10/2017
 */
public class ProductInventoryCacheReloadRequest implements Request {

  private Integer productInventoryId;

  private ProductInventoryService productInventoryService;

  public ProductInventoryCacheReloadRequest(Integer productInventoryId,
      ProductInventoryService productInventoryService) {
    this.productInventoryId = productInventoryId;
    this.productInventoryService = productInventoryService;
  }

  @Override
  public void process() {
    //get product inventory data from db
    ProductInventory productInventory = productInventoryService
        .findProductInventory(productInventoryId);
    //set data to redis cache
    productInventoryService.setProductInventoryCache(productInventory);
  }

  @Override
  public Integer getProductId() {
    return productInventoryId;
  }
}
