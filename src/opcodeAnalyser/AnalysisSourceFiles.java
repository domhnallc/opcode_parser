package opcodeAnalyser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Class contains methods to derive source files from their directories validate
 * them and create array of files to pass to the Parser
 * 
 * @author Domhnall
 * 
 */
public class AnalysisSourceFiles {

	/**
	 * method reqs user input for directories to scan
	 * 
	 * @param fileType
	 * @return
	 */
	public String getSourceFiles() {

		boolean flag = false;
		String directory = null;

		do {

			System.out.println("Enter directory containing files to analyse: ");
			Scanner scanner = new Scanner(System.in);
			directory = scanner.next();

			// check if it is a directory
			Path path = Paths.get(directory);

			// validate input is a current directory
			if (Files.isDirectory(path) && Files.isReadable(path)) {
				System.out.println("Directory is valid.");
				flag = true;

			} else {

				System.out.println("Source is not a valid or readable directory.  Please enter a valid source.");
				// scanner.();
			}
		} while (flag == false);

		return directory;

	}

	/**
	 * method takes source files from directory, validates and returns array of
	 * files to analyse
	 * 
	 * @param directory
	 * @return String[] files to analyse
	 */
	public String[] deriveSourceFiles(String directory) {

		System.out.println("Derive source files");

		File directoryToDerive = new File(directory);
		File[] file = directoryToDerive.listFiles();
		String[] filesToAnalyse = new String[file.length];
		for (int i = 0; i < file.length; i++) {
			// add to array
			if (file[i].exists() && file[i].canRead()) {
				filesToAnalyse[i] = file[i].toString();
				System.out.println("File: " + file[i].toString() + " is valid");
			} else {
				System.out.println(
						"File: " + file[i].toString() + " cannot be read or does not exist and will not be analysed.");
			}
		}
		System.out.println("end of derive method");

		return filesToAnalyse;

	}

	

}
