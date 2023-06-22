package external.impl;

import java.io.IOException;

import external.DatabaseReader;

public class DatabaseReaderImpl implements DatabaseReader {

	@Override
	public int rowInTables(String tableName) throws InterruptedException, IOException {
System.out.println(String.format("Number of rows in table %s is '50'",tableName));
Thread.sleep(1000);

// throw new IOException("Hey not able to connect to database server");
		return 50;
	}

	@Override
	public String[] readColumns(String query) throws InterruptedException {
		System.out.println(String.format("The query is:%s", query));
	    Thread.sleep(1500);
		return new String[] {"col1","col2"};
	}

}
