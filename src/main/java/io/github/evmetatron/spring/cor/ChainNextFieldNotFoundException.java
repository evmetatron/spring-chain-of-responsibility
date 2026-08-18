package io.github.evmetatron.spring.cor;

/**
 * Thrown by {@link ChainFactory#createChain(Class)} when a bean implementing the chain interface
 * has no field annotated with {@link ChainNext @ChainNext}.
 */
public class ChainNextFieldNotFoundException extends RuntimeException {
  ChainNextFieldNotFoundException(Class<?> beanClass) {
    super(
        "No field annotated with @ChainNext found in "
            + beanClass.getName()
            + ". Add a field of the chain interface type annotated with @ChainNext.");
  }
}
