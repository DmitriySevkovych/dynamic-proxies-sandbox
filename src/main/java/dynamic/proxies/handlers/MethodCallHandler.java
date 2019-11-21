package dynamic.proxies.handlers;

@FunctionalInterface // i.e. an interface with only one method without a default implementation
public interface MethodCallHandler {
	Object handle(Object proxy, Object[] args) throws Throwable;
}
