package com.huigod.eshop.inventory.model;

/**
 * @Author: huiGod
 * @Description: inventory count model
 * @Date: 8:54 PM 29/10/2017
 */
public class ProductInventory {

  private Integer productId;

  private Long inventoryCnt;

  public ProductInventory() {
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Long getInventoryCnt() {
    return inventoryCnt;
  }

  public void setInventoryCnt(Long inventoryCnt) {
    this.inventoryCnt = inventoryCnt;
  }

  public ProductInventory(Integer productId, Long inventoryCnt) {
    this.productId = productId;
    this.inventoryCnt = inventoryCnt;
  }
}
