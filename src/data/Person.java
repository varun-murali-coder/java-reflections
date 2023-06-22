package data;

public class Person {
	
	private final String name;
	private final int age;
	private final float salary;
	private final Address address;
	private final Company  company;
	public Person(String name, int age, float salary, Address address,Company  company) {
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.address = address;
		this.company=company;
	}
	
	

}
