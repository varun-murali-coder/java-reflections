package testframework;

public class PaymentServiceTest {
	private PaymentService service;
	
	public static void beforeClass() {
		System.out.println("This class called at the begining once object created");
	}
	public  void setUpTest() {
		System.out.println("Called for each test class");
	}
	
	public static void afterClass() {
		System.out.println("This class called at the end once object destroyed");
	}
	
	
	public  void testCreditCardPayments() {
		System.out.println("Payment via credit card");
	}
	
	public  void testWireTransfer() {
		System.out.println("Payment via wire transfer");

	}
	
	public  void testInsufficientFund() {
		System.out.println("Insufficient fund for transfer activity");
	}
	
	/*helper class-dont add*/
	public static void helperClass() {
		System.out.println("Simple helper");
	}

}
