package com.huigod.eshop.inventory.listener;

import com.huigod.eshop.inventory.threadpool.RequestProcessorThreadPool;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    RequestProcessorThreadPool.init();
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
