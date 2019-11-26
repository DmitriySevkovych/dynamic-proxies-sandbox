package dynamic.proxies.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;

public class Proxies {
	
	/*
	 * Playing around with Java dynamic proxies
	 */
	@SuppressWarnings("unchecked")
	public static <T> T simpleProxy(Class<? extends T> iface, InvocationHandler h, Class<?>... otherIfaces)
	{
		Class<?>[] allInterfaces = Stream.concat(Stream.of(iface), Stream.of(otherIfaces)).distinct()
				.toArray(Class<?>[]::new);

		return (T) Proxy.newProxyInstance(iface.getClassLoader(), allInterfaces, h);
	}

	public static <T> T interceptingProxy(T target, Class<T> iface, MethodCallInterceptor interceptor)
	{
		return simpleProxy(iface, intercepting(handlingDefaultMethods(binding(target)), interceptor));
	}

	/*
	 * Proxies, helper methods
	 */
	private static MethodCallInterpreter binding(Object target, MethodCallInterpreter unboundInterpreter)
	{
		return method -> {
			if (method.getDeclaringClass().isAssignableFrom(target.getClass()))
			{
				return (proxy, args) -> method.invoke(target, args);
			}
			else
			{
				return unboundInterpreter.interpret(method);
			}
		};
	}

	private static MethodCallInterpreter binding(Object target)
	{
		return binding(target, method -> {
			throw new IllegalStateException(
					String.format("Target class %s does not support method %s", target.getClass(), method));
		});
	}

	private static MethodCallInterpreter handlingDefaultMethods(MethodCallInterpreter nonDefaultInterpreter)
	{
		return method -> {
			if (method.isDefault())
			{
				return DefaultMethodExecutor.forMethod(method);
			}
			else
			{
				return nonDefaultInterpreter.interpret(method);
			}
		};
	}

	private static MethodCallInterpreter intercepting(MethodCallInterpreter interpreter,
			MethodCallInterceptor interceptor)
	{
		return method -> interceptor.intercepting(method, interpreter.interpret(method));
	}

}
