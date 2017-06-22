package test;

//Library file imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.text.SimpleDateFormat;

//Converting UnixtoDos Class
public class ConvertingUnixtoDos {
	//Array to store the list of all file names in a directory
	static List<File> files = new ArrayList<File>();
	//Method to add time stamp
	public static void addtimestamp(File folder) {
		int count = 0;
		//Loop to read names of “.req.copied” files into the array list
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println(" i am here");
			} else {				
				if (fileEntry.getAbsolutePath().endsWith(".req.copied")) {
					count++;
					if (count > 5000){
						break;
					}
					files.add(fileEntry);
				}
			}
		}
		System.out.println(" my file count is "+files.size());
		try {
			Iterator<File> it = files.iterator();
			//While loop to iterate logic for each file in the list
			while (it.hasNext()) {
				File file  = it.next();
				System.out.println(" file under process is "+file.getAbsolutePath());
				String line;
				//Object to read time stamp in a specific format
				SimpleDateFormat sdf = new SimpleDateFormat("|MM/dd/yyyy|HH:mm|");
				line = new String("TIMESTAMP=");
				line = line.concat(sdf.format(file.lastModified()));
				//Appending the time stamp of the file to the file
				FileWriter fw = new FileWriter(file, true);		
				fw.write(line);
				fw.write("\r\n");
				fw.close();
				it.remove();
			}
			//Function call to run unix2dos command in command line
			Runtime.getRuntime().exec("C:/cygwin64/bin/unix2dos C:/temp/sftp/test/*.req.copied");
		}
	      catch (IOException e) {
			e.printStackTrace();
		} finally {}
	}
	public static void main(String[] args) {
		File path = new File("C:\\temp\\sftp\\test");
		addtimestamp(path);
	}
}
