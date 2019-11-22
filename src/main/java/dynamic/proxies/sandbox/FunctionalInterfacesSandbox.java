package dynamic.proxies.sandbox;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import dynamic.proxies.entities.Author;

public class FunctionalInterfacesSandbox {

	public static void main(String[] args)
	{
		// Dummy data
		List<Author> people = Arrays.asList(new Author("Charles", "Dickens", 60), new Author("Charlotte", "Bronte", 45),
				new Author("Lewis", "Carroll", 42), new Author("James", "Joyce", 36),
				new Author("Wilkie", "Collins", 30));

		print(people, p -> p.getLastname().startsWith("C"));

		perform(people, p -> p.getAge() > 40, p -> System.out.println(p.greet()));

	}

	/*
	 * Playing around with functional interfaces
	 */
	public static void print(List<Author> people, Predicate<Author> predicate)
	{
		// Print person information if the person has the provided predicate (Lambda
		// expression)
		for (Author p : people)
		{
			if (predicate.test(p))
			{
				System.out.println(p.toString());
			}
		}
	}

	public static void perform(List<Author> people, Predicate<Author> predicate, Consumer<Author> consumer)
	{
		// Do 'something' (i.e. consume second Lambda expression) if the person has the
		// provided predicate (first Lambda expression)
		for (Author p : people)
		{
			if (predicate.test(p))
			{
				consumer.accept(p);
			}
		}
	}

}
