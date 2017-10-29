package com.huigod.eshop.inventory.mapper;


import com.huigod.eshop.inventory.model.ProductInventory;
import org.apache.ibatis.annotations.Param;

public interface ProductInventoryMapper {

  /**
   * @Author: huiGod
   * @Description: upate product inventory count to db
   * @Date: 8:52 PM 29/10/2017
   */
  void updateProductInventory(ProductInventory inventoryCnt);

  /**
   * @Author: huiGod
   * @Description: retrieve product inventory data from db
   * @Date: 9:21 PM 29/10/2017
   */
  ProductInventory findProductInventory(@Param("productId") Integer productId);

}
