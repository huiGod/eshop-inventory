package com.huigod.eshop.inventory.service;

import com.huigod.eshop.inventory.model.ProductInventory;
/**
 * @Author: huiGod
 * @Description: product inventory interface
 * @Date: 9:10 PM 29/10/2017
 */
public interface ProductInventoryService {

  /**
   * @Author: huiGod
   * @Description: update product inventory
   * @Date: 9:10 PM 29/10/2017
   */
  void updateProductInventory(ProductInventory productInventory);

  /**
   * @Author: huiGod
   * @Description: delete product inventory cache in redis
   * @Date: 9:10 PM 29/10/2017
   */
  void removeProductInventoryCache(ProductInventory productInventory);

  /**
   * @Author: huiGod
   * @Description: get product inventory from db
   * @Date: 9:26 PM 29/10/2017
   */
  ProductInventory findProductInventory(Integer productId);

  /**
   * @Author: huiGod
   * @Description: set product inventory to redis cache
   * @Date: 9:29 PM 29/10/2017
   */
  void setProductInventoryCache(ProductInventory productInventory);
}
