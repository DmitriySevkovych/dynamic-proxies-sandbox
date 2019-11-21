package dynamic.proxies.entities;

import lombok.Value;

@Value
public class Person {
	/*
	 * Fields
	 */
	private final String firstname;
	private final String lastname;
	private int age;

	/*
	 * Constructors
	 */
	public Person(String firstname, String lastname, int age)
	{
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
	}

	/*
	 * Methods
	 */
	public String greet()
	{
		return "Hi, my name is " + this.toString();
	}
	
	@Override
	public String toString()
	{
		return this.firstname + " " + this.lastname;
	}
}
