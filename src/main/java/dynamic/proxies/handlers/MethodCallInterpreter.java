package dynamic.proxies.handlers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@FunctionalInterface
public interface MethodCallInterpreter extends InvocationHandler {

	/*
	 * Functional interface
	 */
	MethodCallExecutor interpret(Method method);

	/*
	 * extends InvocationHandler
	 */
	@Override
	default Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		// Interpret the called method, i.e. find the correct executor for the method
		// call
		MethodCallExecutor executor = interpret(method);

		// Invoke method call execution
		return executor.invoke(proxy, args);
	}
}
