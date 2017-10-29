package com.huigod.eshop.inventory.service.impl;

import com.huigod.eshop.inventory.dao.RedisDAO;
import com.huigod.eshop.inventory.mapper.ProductInventoryMapper;
import com.huigod.eshop.inventory.model.ProductInventory;
import com.huigod.eshop.inventory.service.ProductInventoryService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("productInventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService {

  @Resource
  private ProductInventoryMapper productInventoryMapper;
  @Resource
  private RedisDAO redisDAO;

  @Override
  public void updateProductInventory(ProductInventory productInventory) {
    productInventoryMapper.updateProductInventory(productInventory);
  }

  @Override
  public void removeProductInventoryCache(ProductInventory productInventory) {
    String key = "product:inventory:" + productInventory.getProductId();
    redisDAO.delete(key);
  }

  @Override
  public ProductInventory findProductInventory(Integer productId) {
    return productInventoryMapper.findProductInventory(productId);
  }

  @Override
  public void setProductInventoryCache(ProductInventory productInventory) {
    String key = "product:inventory:" + productInventory.getProductId();
    redisDAO.set(key,String.valueOf(productInventory.getInventoryCnt()));
  }
}
