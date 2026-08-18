package io.github.evmetatron.spring.cor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers a {@link ChainFactory} bean.
 *
 * <p>Picked up automatically by Spring Boot's autoconfiguration mechanism. In a plain Spring
 * context, import this class explicitly, e.g. {@code @Import(ChainAutoConfiguration.class)}.
 */
@Configuration(proxyBeanMethods = false)
public class ChainAutoConfiguration {
  @Bean
  public ChainFactory chainFactory(@Autowired ApplicationContext context) {
    return new ChainFactory(context);
  }
}
