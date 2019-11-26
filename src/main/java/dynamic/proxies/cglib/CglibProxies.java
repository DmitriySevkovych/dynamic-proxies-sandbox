package dynamic.proxies.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class CglibProxies {

	public static Object proxy(Class<?> target, Callback callback) {
		Enhancer enhancer = new Enhancer();

		enhancer.setSuperclass(target);
		enhancer.setCallback(callback);

		return enhancer.create();
	}

	public static Object noOpProxy(Class<?> target) {
		Enhancer enhancer = new Enhancer();

		enhancer.setSuperclass(target);
		enhancer.setCallback(NoOp.INSTANCE);

		return enhancer.create();
	}

	public static Object noOpProxy(Class<?> target, Class<?>[] args, Object[] argVals) {
		Enhancer enhancer = new Enhancer();

		enhancer.setSuperclass(target);
		enhancer.setCallback(NoOp.INSTANCE);

		return enhancer.create(args, argVals);
	}

	public static Object interceptorProxy(Class<?> target, String interceptorMessage) {
		Enhancer enhancer = new Enhancer();

		enhancer.setSuperclass(target);
		enhancer.setCallback(new CglibInterceptor(interceptorMessage));

		return enhancer.create();
	}
}
