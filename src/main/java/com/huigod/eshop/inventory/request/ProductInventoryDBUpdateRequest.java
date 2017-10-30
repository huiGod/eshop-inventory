package com.huigod.eshop.inventory.request;

import com.huigod.eshop.inventory.model.ProductInventory;
import com.huigod.eshop.inventory.service.ProductInventoryService;

/**
 * @Author: huiGod
 * @Description: 库存的修改，cache aside pattern  1.删除缓存  2.更新数据库
 * @Date: 8:44 PM 29/10/2017
 */
public class ProductInventoryDBUpdateRequest implements Request {

  private ProductInventory productInventory;

  private ProductInventoryService productInventoryService;

  public ProductInventoryDBUpdateRequest(ProductInventory productInventory,
      ProductInventoryService productInventoryService) {
    this.productInventory = productInventory;
    this.productInventoryService = productInventoryService;
  }

  @Override
  public void process() {
    //delete cache in redis
    productInventoryService.removeProductInventoryCache(productInventory);

    //update data in DB
    productInventoryService.updateProductInventory(productInventory);
  }

  @Override
  public Integer getProductId(){
    return productInventory.getProductId();
  }
}
