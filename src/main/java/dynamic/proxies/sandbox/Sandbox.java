package dynamic.proxies.sandbox;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import dynamic.proxies.entities.Person;

public class Sandbox {

	public static void main(String[] args)
	{
		List<Person> people = Arrays.asList(new Person("Charles", "Dickens", 60), new Person("Charlotte", "Bronte", 45),
				new Person("Lewis", "Carroll", 42), new Person("James", "Joyce", 36),
				new Person("Wilkie", "Collins", 30));

		print(people, p -> p.getLastname().startsWith("C"));
		
		perform(people, p -> p.getAge() > 40, p -> System.out.println(p.greet()));

	}

	/*
	 * Playing around with functional interfaces
	 */
	public static void print(List<Person> people, Predicate<Person> predicate)
	{
		// Print person information if the person has the provided predicate (Lambda
		// expression)
		for (Person p : people)
		{
			if (predicate.test(p))
			{
				System.out.println(p.toString());
			}
		}
	}

	public static void perform(List<Person> people, Predicate<Person> predicate, Consumer<Person> consumer)
	{
		// Do 'something' (i.e. consume second Lambda expression) if the person has the
		// provided predicate (first Lambda expression)
		for (Person p : people)
		{
			if (predicate.test(p))
			{
				consumer.accept(p);
			}
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
}
