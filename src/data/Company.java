package data;

public class Company {

	private final String  companyName;
	private final String location;
	private Address address;
	public Company(String companyName, String location, Address address) {
		super();
		this.companyName = companyName;
		this.location = location;
		this.address = address;
	}
	
	
}
