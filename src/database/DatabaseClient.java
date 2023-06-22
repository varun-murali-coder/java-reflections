package database;

public class DatabaseClient {

	public boolean storeData(String data) {
		System.out.println(String.format("Data stored in database: ", data));
		return true;
	}
}
