package io.github.evmetatron.spring.cor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the field that {@link ChainFactory} injects with the next bean in the chain.
 *
 * <p>The annotated field's type must be the chain interface passed to {@link
 * ChainFactory#createChain(Class)}. Every bean participating in a chain must declare exactly one
 * such field, including the last one in order &mdash; it receives a no-op implementation so it can
 * be called unconditionally.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChainNext {}
