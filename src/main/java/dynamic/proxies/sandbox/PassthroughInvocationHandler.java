package dynamic.proxies.sandbox;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PassthroughInvocationHandler implements InvocationHandler {

	/*
	 * Fields
	 */
	private final Object target;

	/*
	 * Constructors
	 */
	public PassthroughInvocationHandler(Object target)
	{
		this.target = target;
	}

	/*
	 * implements InvocationHandler
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		System.out.println("PassthroughInvocationHandler active");
		
		// REMARK: the proxy is not used here! Any method call is just passed through to
		// the provided target object of this InvocationHandler
		return method.invoke(this.target, args);
	}

}
