package com.huigod.eshop.inventory;

import com.huigod.eshop.inventory.listener.InitListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @Author: huiGod
 * @Description: 库存服务的Application类
 * @Date: 10:54 AM 28/10/2017
 */
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@MapperScan("com.huigod.eshop.inventory.mapper")
public class Application {

  /**
   * @Author: huiGod
   * @Description: 构建数据源
   * @Date: 11:12 AM 28/10/2017
   */
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return new DataSource();
  }

  /**
   * @Author: huiGod
   * @Description: 构建MyBatis的入口类 SqlSessionFactory
   * @Date: 11:13 AM 28/10/2017
   */
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource());
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
    return sqlSessionFactoryBean.getObject();
  }


  /**
   * @Author: huiGod
   * @Description: 构建事务管理器
   * @Date: 11:22 AM 28/10/2017
   */
  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  /**
   * @Author: huiGod
   * @Description: 构建redis cluster集群
   * @Date: 5:56 PM 29/10/2017
   */
  @Bean
  public JedisCluster JedisClusterFactory() {
    Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
    jedisClusterNodes.add(new HostAndPort("192.168.1.107", 7001));
    jedisClusterNodes.add(new HostAndPort("192.168.1.104", 7003));
    jedisClusterNodes.add(new HostAndPort("192.168.1.105", 7005));
    JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
    return jedisCluster;
  }

  /**
   * @Author: huiGod
   * @Description: 注册监听器
   * @Date: 5:57 PM 29/10/2017
   */
  @Bean
  public ServletListenerRegistrationBean servletListenerRegistrationBean() {
    ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
    servletListenerRegistrationBean.setListener(new InitListener());
    return servletListenerRegistrationBean;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}


