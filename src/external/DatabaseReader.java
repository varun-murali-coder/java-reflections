package external;

import java.io.IOException;

public interface DatabaseReader {

	public int rowInTables(String tableNames) throws InterruptedException, IOException;
	public String[] readColumns(String query) throws InterruptedException;
}
