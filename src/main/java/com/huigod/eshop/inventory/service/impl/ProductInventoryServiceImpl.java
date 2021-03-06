package com.huigod.eshop.inventory.service.impl;

import com.huigod.eshop.inventory.dao.RedisDAO;
import com.huigod.eshop.inventory.mapper.ProductInventoryMapper;
import com.huigod.eshop.inventory.model.ProductInventory;
import com.huigod.eshop.inventory.service.ProductInventoryService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("productInventoryService")
public class ProductInventoryServiceImpl implements ProductInventoryService {

  @Resource
  private ProductInventoryMapper productInventoryMapper;
  @Resource
  private RedisDAO redisDAO;

  @Override
  public void updateProductInventory(ProductInventory productInventory) {
    productInventoryMapper.updateProductInventory(productInventory);
    System.out.println(
        "===========日志===========:已修改数据库中的库存，商品id=" + productInventory.getProductId() + ",商品库存="
            + productInventory.getInventoryCnt());
  }

  @Override
  public void removeProductInventoryCache(ProductInventory productInventory) {
    String key = "product:inventory:" + productInventory.getProductId();
    redisDAO.delete(key);
    System.out.println("===========日志===========:已删除redis中的缓存：key=" + key);
  }

  @Override
  public ProductInventory findProductInventory(Integer productId) {
    return productInventoryMapper.findProductInventory(productId);
  }

  @Override
  public void setProductInventoryCache(ProductInventory productInventory) {
    String key = "product:inventory:" + productInventory.getProductId();
    redisDAO.set(key, String.valueOf(productInventory.getInventoryCnt()));
    System.out
        .println(
            "===========日志===========:已更新商品库存的缓存，商品id=" + productInventory.getProductId() + ",key="
                + key);
  }

  @Override
  public ProductInventory getProductInventoryCache(Integer productId) {
    Long inventoryCnt;
    String key = "product:inventory:" + productId;
    String result = redisDAO.get(key);
    if (!StringUtils.isEmpty(result)) {
      try {
        inventoryCnt = Long.valueOf(result);
        return new ProductInventory(productId, inventoryCnt);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
