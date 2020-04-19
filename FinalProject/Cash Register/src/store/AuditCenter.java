package store;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class AuditCenter {
	
	public static void audit(String action) throws IOException {
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		

		FileWriter fileWriter = new FileWriter("C:\\Users\\domo\\Desktop\\AuditFile.csv", true);
	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	    
	    bufferedWriter.append(action + "," + ts + "\n");
	    bufferedWriter.close();
	}
}
