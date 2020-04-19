package store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadWriteFile {
	 private static ReadWriteFile single_instance = null; 
	
	private ReadWriteFile() {
		
	}
	
	public static ReadWriteFile getInstance() {
		if(single_instance == null) {
			single_instance = new ReadWriteFile();
		}
		

		return single_instance;
	}
	
	public List<String[]> read(File file) throws IOException{
		List<String[]> rows = new ArrayList<>();
		
		Scanner scanner = new Scanner(file); 
		while (scanner.hasNextLine()) {
			rows.add(scanner.nextLine().split(","));
		}
		
		return rows;
	}
	
	public void write(File file, String t) throws IOException {
		FileWriter fileWriter = new FileWriter(file, true);
	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	    
	    bufferedWriter.append(t + "\n");
	    bufferedWriter.close();
	}
	
}
