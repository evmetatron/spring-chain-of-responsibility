package io.github.evmetatron.spring.cor;

public class ChainNextFieldNotFoundException extends RuntimeException {
  ChainNextFieldNotFoundException(Class<?> beanClass) {
    super(
        "No field annotated with @ChainNext found in "
            + beanClass.getName()
            + ". Add a field of the chain interface type annotated with @ChainNext.");
  }
}
