package dynamic.proxies.javassist;

import java.lang.reflect.InvocationTargetException;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

public class JavassistProxies
{

  public static Object proxy(Class<?> target, Class<?>[] args, Object[] argVals, MethodHandler handler)
      throws NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException,
      InvocationTargetException
  {
    ProxyFactory f = new ProxyFactory();
    f.setSuperclass(target);

    return f.create(args, argVals, handler);
  }
}
