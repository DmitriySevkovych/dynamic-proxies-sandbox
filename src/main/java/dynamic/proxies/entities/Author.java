package dynamic.proxies.entities;

import lombok.Data;

@Data
public class Author implements Person {
	/*
	 * Fields
	 */
	private final String firstname;
	private final String lastname;
	private int age;

	/*
	 * Constructors
	 */
	public Author(String firstname, String lastname, int age)
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
