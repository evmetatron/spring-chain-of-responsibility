package io.github.evmetatron.spring.cor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class ChainAutoConfiguration {
  @Bean
  ChainFactory chainFactory(@Autowired ApplicationContext context) {
    return new ChainFactory(context);
  }
}
