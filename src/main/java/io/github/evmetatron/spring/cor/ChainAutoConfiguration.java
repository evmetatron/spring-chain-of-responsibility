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
  /** Creates the autoconfiguration. */
  public ChainAutoConfiguration() {}

  /**
   * Registers the {@link ChainFactory} bean.
   *
   * @param context the application context to autowire into the factory
   * @return a {@link ChainFactory} backed by {@code context}
   */
  @Bean
  public ChainFactory chainFactory(@Autowired ApplicationContext context) {
    return new ChainFactory(context);
  }
}
