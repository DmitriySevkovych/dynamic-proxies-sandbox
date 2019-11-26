package dynamic.proxies.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dynamic.proxies.cglib.CglibInterceptor;
import dynamic.proxies.cglib.CglibProxies;
import dynamic.proxies.entities.Author;
import dynamic.proxies.entities.Person;
import dynamic.proxies.entities.Publisher;
import dynamic.proxies.java.MethodCallInterceptor;
import dynamic.proxies.java.Proxies;

public class DynamicProxiesSandbox {

	public static void main(String[] args) {
		// Dummy data
		Person author = new Author("Charles", "Dickens", 24);

		// List for storing information about intercepted method calls
		List<String> callDetails = new ArrayList<>();

		// A method call interceptor
		MethodCallInterceptor concreteInterceptor = (proxy, method, arguments, executor) -> {
			Object result = executor.invoke(proxy, arguments);
			callDetails.add(String.format("%s: %s -> %s", method.getName(), Arrays.toString(arguments), result));
			return result;
		};

		Person proxy = Proxies.interceptingProxy(author, Person.class, concreteInterceptor);
		proxy.setAge(28);
		proxy.getFirstname();
		proxy.getLastname();

		// Print test data
		for (String call : callDetails) {
			System.out.println(call);
		}

		/*
		 * CGLIB
		 */
//		Class<?>[] proxyArgs = { String.class };
//		Object[] proxyArgVals = { "Penguin Classics" };

//		Publisher publisherNoOpProxy = (Publisher) CglibProxies.noOpProxy(Publisher.class);
//		System.out.println(publisherNoOpProxy.employ((Author) author));
		
//		Publisher publisherInterceptorProxy = (Publisher) CglibProxies.interceptorProxy(Publisher.class, "Penguin Classics");
//		System.out.println(publisherInterceptorProxy.employ((Author) author));
		
		Publisher publisherProxy = (Publisher) CglibProxies.proxy(Publisher.class, new CglibInterceptor("Penguin Classics is best!"));
		System.out.println(publisherProxy.employ((Author) author));
	}

}
