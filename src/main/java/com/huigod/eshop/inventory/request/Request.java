package com.huigod.eshop.inventory.request;

/**
 * @Author: huiGod
 * @Description: 请求接口
 * @Date: 6:15 PM 29/10/2017
 */
public interface Request {

  void process();

  Integer getProductId();
}
