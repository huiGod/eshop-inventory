package com.huigod.eshop.inventory.service;

import com.huigod.eshop.inventory.model.User;

/**
 * @Author: huiGod
 * @Description: 用户Service接口
 * @Date: 10:53 AM 29/10/2017
 */
public interface UserService {

  User findUserInfo();

  User getCachedUserInfo();
}
