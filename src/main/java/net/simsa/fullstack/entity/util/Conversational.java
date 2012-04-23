package net.simsa.fullstack.entity.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Indicates that the marked method should be run in a Conversation scope context, so that the EntityManager
 * remains available for the entire conversation (possibly more than one request/response cycle).
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@InterceptorBinding
public @interface Conversational {
}