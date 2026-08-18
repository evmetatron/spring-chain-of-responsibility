package io.github.evmetatron.spring.cor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.ReflectionUtils;

/**
 * Builds a Chain of Responsibility from Spring beans that implement a common interface.
 *
 * <p>Beans implementing the given interface are collected from the {@link ApplicationContext},
 * ordered using {@link org.springframework.core.annotation.Order @Order}, and linked together by
 * injecting each bean's {@link ChainNext @ChainNext}-annotated field with the next bean in the
 * chain.
 *
 * <p>With Spring Boot, a {@code ChainFactory} bean is registered automatically via {@link
 * ChainAutoConfiguration}. In a plain Spring context, either import {@code ChainAutoConfiguration}
 * or construct this class directly.
 */
public class ChainFactory {
  private final ApplicationContext context;

  /**
   * Creates a factory that looks up chain beans in the given context.
   *
   * @param context the Spring context to collect chain beans from
   */
  public ChainFactory(ApplicationContext context) {
    this.context = context;
  }

  /**
   * Collects all beans implementing {@code chainInterface}, orders them via {@code @Order}, and
   * wires each one's {@code @ChainNext} field to the next bean in the chain.
   *
   * <p>The last bean in the chain, and the return value when no beans implement the interface,
   * receive a no-op proxy as their next link so calling into an unconfigured tail never throws.
   *
   * @param chainInterface the chain interface to build a chain for
   * @param <T> the chain interface type
   * @return the first bean in order, i.e. the entry point of the chain
   * @throws ChainNextFieldNotFoundException if a bean implementing {@code chainInterface} has no
   *     field annotated with {@code @ChainNext}
   */
  public <T> T createChain(Class<T> chainInterface) {
    List<T> beans = new ArrayList<>(context.getBeansOfType(chainInterface).values());

    if (beans.isEmpty()) {
      return NoOpProxyFactory.create(chainInterface);
    }

    beans.sort(AnnotationAwareOrderComparator.INSTANCE);

    for (int i = 0; i < beans.size(); i++) {
      T current = beans.get(i);
      T next = (i + 1 < beans.size()) ? beans.get(i + 1) : NoOpProxyFactory.create(chainInterface);

      injectNext(current, next);
    }

    return beans.get(0);
  }

  private <T> void injectNext(T current, T next) {
    AtomicBoolean injected = new AtomicBoolean(false);

    ReflectionUtils.doWithFields(
        AopUtils.getTargetClass(current),
        field -> {
          if (field.isAnnotationPresent(ChainNext.class)) {
            field.setAccessible(true);
            field.set(current, next);
            field.setAccessible(false);
            injected.set(true);
          }
        });

    if (!injected.get()) {
      throw new ChainNextFieldNotFoundException(AopUtils.getTargetClass(current));
    }
  }
}
