package opcodeAnalyser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Filter {

	public ArrayList<String> filter(LinkedHashMap<String, Integer> countOpCodes) {

		// create arraylist to hold post-filter opcodes
		ArrayList<String> updatedOpCodes = new ArrayList<>();

		// iterate through map
		// for (int i = 0; i < countOpCodes.size(); i++) {
		// check if each entry is >= filterValue
		// if (countOpCodes.containsValue(0)) {

		for (String entry : countOpCodes.keySet()) {
			if (countOpCodes.get(entry) != 0) {
				// countOpCodes.remove(entry);
				// System.out.println(entry);
				updatedOpCodes.add(entry);
				// }
				// }
			}

		}

		// print postfilteropcodes
		System.out.println("\nPost-Filter Opcodes: \n");
		for (String code : updatedOpCodes) {
			//System.out.print(code + " ");
		}
		
		System.out.println("\nPostfilter opcodes="+updatedOpCodes.size());

		// return filteredCodeMap;
		return updatedOpCodes;
	}

	/**
	 * method takes a ArrayList of filtered opcodes and writes to text file
	 * 
	 * @param updatedOpCodesToWrite
	 */
	public void postFilterOpcodeFileWriter(ArrayList<String> updatedOpCodesToWrite) {

		String fileToWrite = "postFilterOpCodes.txt";

		File file = new File(fileToWrite);
		if (file.exists()) {
			file.delete();
		}

		try {

			// if exists, write data to file
			FileWriter fileWriter = new FileWriter(file.getName(), false);// false
																			// to
																			// overwrite
			// buffered writer for efficiency with multiple files
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// write rawOpcodesList as headers to the file
			Iterator<String> updatedOpCodesToWriteIt = updatedOpCodesToWrite.iterator();
			while (updatedOpCodesToWriteIt.hasNext()) {
				bufferedWriter.write(" " + updatedOpCodesToWriteIt.next() + " " + System.getProperty("line.separator"));

				// bufferedWriter.flush();

			}

			bufferedWriter.close();// if you remove this close statement,
									// nothing writes to the file

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

}
