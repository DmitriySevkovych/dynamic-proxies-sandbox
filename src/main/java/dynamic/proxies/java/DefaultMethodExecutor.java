package dynamic.proxies.java;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultMethodExecutor {

	/*
	 * Fields
	 */
	private static final ConcurrentMap<Method, MethodCallExecutor> cache = new ConcurrentHashMap<>();

	/*
	 * Methods
	 */
	public static MethodCallExecutor forMethod(Method method)
	{
		// REMARK: the caching is optional but part of the tutorial that I am following,
		// so why not...
		return cache.computeIfAbsent(method, m -> {
			MethodHandle handle = getMethodHandle(m);

			// REM: the return type corresponds to the functional interface MethodExecutor!
			return (proxy, args) -> handle.bindTo(proxy).invokeWithArguments(args);
		});
	}

	private static MethodHandle getMethodHandle(Method method)
	{
		Class<?> declaringClass = method.getDeclaringClass();

		try
		{
			Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
					.getDeclaredConstructor(Class.class, int.class);
			constructor.setAccessible(true);

			return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE).unreflectSpecial(method,
					declaringClass);
		}
		catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}

	}

}
