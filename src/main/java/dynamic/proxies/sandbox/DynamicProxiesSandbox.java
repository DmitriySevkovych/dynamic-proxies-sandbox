package dynamic.proxies.sandbox;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import dynamic.proxies.entities.Author;
import dynamic.proxies.entities.Person;
import dynamic.proxies.handlers.DefaultMethodExecutor;
import dynamic.proxies.handlers.MethodCallInterceptor;
import dynamic.proxies.handlers.MethodCallInterpreter;

public class DynamicProxiesSandbox {

	public static void main(String[] args)
	{
		// Dummy data
		Person person = new Author("Charles", "Dickens", 24);

		// List for storing information about intercepted method calls
		List<String> callDetails = new ArrayList<>();

		// A method call interceptor
		MethodCallInterceptor concreteInterceptor = (proxy, method, arguments, executor) -> {
			Object result = executor.invoke(proxy, arguments);
			callDetails.add(String.format("%s: %s -> %s", method.getName(), Arrays.toString(arguments), result));
			return result;
		};

		Person proxy = interceptingProxy(person, Person.class, concreteInterceptor);
		proxy.setAge(28);
		proxy.getFirstname();
		proxy.getLastname();
		
		// Print test data
		for (String call : callDetails)
		{
			System.out.println(call);
		}
	}

	/*
	 * Playing around with dynamic proxies
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
