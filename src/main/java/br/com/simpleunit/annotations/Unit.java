package br.com.simpleunit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.simpleunit.util.None;

@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
public @interface Unit {
	Class<? extends Throwable> shouldRise() default None.class;
}
