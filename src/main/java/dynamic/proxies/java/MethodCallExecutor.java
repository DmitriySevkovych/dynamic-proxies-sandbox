package dynamic.proxies.java;

@FunctionalInterface // i.e. an interface with only one method without a default implementation
public interface MethodCallExecutor {
	Object invoke(Object proxy, Object[] args) throws Throwable;
}
