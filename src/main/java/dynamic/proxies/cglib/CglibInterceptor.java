package dynamic.proxies.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibInterceptor implements MethodInterceptor {

	private final String interceptMessage;

	public CglibInterceptor(String interceptMessage) {
		this.interceptMessage = interceptMessage;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

		System.out.println("BREAKING NEWS: " + interceptMessage);
		return proxy.invokeSuper(obj, args);
	}

}
