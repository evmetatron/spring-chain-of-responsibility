package io.github.evmetatron.spring.cor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class NoOpProxyFactory {
  @SuppressWarnings("unchecked")
  static <T> T create(Class<T> chainClass) {
    return (T)
        Proxy.newProxyInstance(
            chainClass.getClassLoader(),
            new Class[] {chainClass},
            (proxy, method, args) -> getDefaultValue(method));
  }

  private static Object getDefaultValue(Method method) {
    Class<?> returnType = method.getReturnType();

    if (returnType.equals(void.class)) {
      return null;
    }

    if (returnType.equals(boolean.class)) {
      return false;
    }

    if (returnType.isPrimitive()) {
      return 0;
    }

    return null;
  }
}
