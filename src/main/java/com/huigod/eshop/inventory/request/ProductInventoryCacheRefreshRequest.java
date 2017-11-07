package com.huigod.eshop.inventory.request;


import com.huigod.eshop.inventory.model.ProductInventory;
import com.huigod.eshop.inventory.service.ProductInventoryService;

/**
 * @Author: huiGod
 * @Description: reload product inventory cache
 * @Date: 9:19 PM 29/10/2017
 */
public class ProductInventoryCacheRefreshRequest implements Request {

  private Integer productId;

  private ProductInventoryService productInventoryService;

  private boolean forceRefresh;

  public ProductInventoryCacheRefreshRequest(Integer productId,
      ProductInventoryService productInventoryService, boolean forceRefresh) {
    this.productId = productId;
    this.productInventoryService = productInventoryService;
    this.forceRefresh = forceRefresh;
  }

  @Override
  public void process() {
    //get product inventory data from db
    ProductInventory productInventory = productInventoryService
        .findProductInventory(productId);
    System.out.println(
        "===========日志===========: 已查询到商品最新的库存数量，商品id=" + productId + ", 商品库存数量=" + productInventory
            .getInventoryCnt());
    //set data to redis cache
    productInventoryService.setProductInventoryCache(productInventory);
  }

  @Override
  public Integer getProductId() {
    return productId;
  }

  @Override
  public boolean isForceRefresh() {
    return forceRefresh;
  }
}
