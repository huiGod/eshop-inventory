package com.huigod.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.huigod.eshop.inventory.dao.RedisDAO;
import com.huigod.eshop.inventory.mapper.UserMapper;
import com.huigod.eshop.inventory.model.User;
import com.huigod.eshop.inventory.service.UserService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{

  @Resource
  private UserMapper userMapper;

  @Resource
  private RedisDAO redisDAO;

  @Override
  public User findUserInfo() {
    return userMapper.findUserInfo();
  }

  @Override
  public User getCachedUserInfo() {
    redisDAO.set("cached_user_lisi", "{\"name\":\"lisi\",\"age\":28}");

    String userJSON = redisDAO.get("cached_user_lisi");
    JSONObject userJSONObject = JSONObject.parseObject(userJSON);

    User user = new User();
    user.setName(userJSONObject.getString("name"));
    user.setAge(userJSONObject.getInteger("age"));
    return user;
  }
}
