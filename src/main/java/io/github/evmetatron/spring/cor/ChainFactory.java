package io.github.evmetatron.spring.cor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.ReflectionUtils;

public class ChainFactory {
  private final ApplicationContext context;

  public ChainFactory(ApplicationContext context) {
    this.context = context;
  }

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
