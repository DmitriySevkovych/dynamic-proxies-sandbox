package dynamic.proxies.handlers;

import java.lang.reflect.Method;

@FunctionalInterface
public interface MethodCallInterceptor {

	/*
	 * Functional interface
	 */
	Object intercept(Object proxy, Method method, Object[] args, MethodCallExecutor executor) throws Throwable;

	/*
	 * Default methods
	 */
	default MethodCallExecutor intercepting(Method method, MethodCallExecutor executor)
	{
		return (proxy, args) -> intercept(proxy, method, args, executor);
	}

}
