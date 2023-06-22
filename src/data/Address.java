package data;

public class Address {

	private String street;
	private short apartment;
	public Address(String street, short apartment) {
		this.street = street;
		this.apartment = apartment;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public short getApartment() {
		return apartment;
	}
	public void setApartment(short apartment) {
		this.apartment = apartment;
	}
	
	
	
	
}
