//Copyright (C) Queen's University of Belfast 2015-2022. 
//This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.

package opcodeAnalyser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Parser {

	// parameter to pass the raw data file to the parser object
	private String filename;
	// Arraylist to contain the iterated lines of the raw data file
	private ArrayList<String> lines = new ArrayList<String>();

	// collection to hold the rawOpCode List
	public static List<String> rawOpCodesList = new ArrayList<String>();

	//private File file= new File("/media/admin2/MALWARE/cryptominingTraces/benignWebsites_injected.csv");
	private File file= new File("../../sample.csv");

	String lineOut;
	String rawOpCode;//
	public double total;

	public Parser() {

	}

	public Parser(String filename) {
		this.filename = filename;

	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		// TODO: validation for filename here
		this.filename = filename;
	}

	/**
	 * method takes raw data file, parses as strings and adds to lines arraylist
	 * 
	 * @param filename
	 * @return
	 */
	public ArrayList<String> fileToArray(String filename) {

		// String to hold the line being read
		String line;

		// create object of file to be read, pass filename parameter
		File fileToRead = new File(filename);
		// create file reader, pass file to read
		try {
			FileReader fileReader = new FileReader(fileToRead);
			// create buffered filereader, pass fileReader
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			// check for existing next line of raw file
			while ((line = bufferedReader.readLine()) != null) {
				// add line to the array list
				lines.add(line);

			}
			fileReader.close();
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File error: file not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading file.");
			e.printStackTrace();
		}

		return lines;

	}

	public void writeHeaders(boolean flag) {

		/*if (flag == false) {
			file = new File("C:\\Goodware_programSlices_1000\\malware_1000.csv");
		} else {
			file = new File("C:\\Goodware_programSlices_1000\\malware_1000_noZeroes.csv");
		}*/
		
		//file = new File("C:\\Goodware_programSlices_1000\\malware_1000x.csv");

		try {

			// if doesnt exist, create
			if (!file.exists()) {
				file.createNewFile();

			}

			//file.createNewFile();

			if (!file.canWrite()) {
				System.out.println("Warning, CSV file is already open and will not be written to.");
			}
			// if exists, append data to file
			FileWriter fileWriter = new FileWriter(file, true);
			// buffered writer for efficiency with multiple files
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// write rawOpcodesList as headers to the file
			Iterator<String> rawOpcodeListIt = rawOpCodesList.iterator();
			bufferedWriter.append("Filename,");
			while (rawOpcodeListIt.hasNext()) {
				bufferedWriter.append(rawOpcodeListIt.next() + ",");

			}
			// write line break
			bufferedWriter.write(System.getProperty("line.separator"));
			// bufferedWriter.flush();

			// HERE BE DRAGONS! If you remove this close statement, nothing will
			// write to file
			bufferedWriter.close();

		} catch (FileNotFoundException e) {
			System.out.println("CSV file error, file is in use by another process and will not be written to");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File error.");
		}

	}

	public void readRawOpCodes(String opCodesToRead) {
		// fill rawOpCodesArrayList from file
		File rawOpCodeListFile = new File(opCodesToRead);
		// create file reader, pass file to read
		// TODO try with resources
		if (rawOpCodesList.isEmpty()) {
			try {
				FileReader fileReader = new FileReader(rawOpCodeListFile);
				// create buffered filereader, pass fileReader
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				// TODO: close readers
				// check for existing next line of raw file
				while ((rawOpCode = bufferedReader.readLine()) != null) {


					if (rawOpCode.equals("RETN")) {
						rawOpCodesList.add(" " + "RETN");// this corrects the
															// RETN with no
															// following space
															// problem
					} else if (rawOpCode.equals("NOP")) {
						rawOpCodesList.add(" " + "NOP");
					} else if (rawOpCode.equals("CLD")) {
						rawOpCodesList.add(" " + "CLD");
					} else if (rawOpCode.equals("WAIT")) {
						rawOpCodesList.add(" " + "WAIT");
					} else {
						// add line to the array list
						rawOpCodesList.add(" " + rawOpCode + " ");
					}
					

				}
				fileReader.close();
				bufferedReader.close();

			} catch (FileNotFoundException e) {
				// TODO more detailed error handling
				e.printStackTrace();
			} catch (IOException e) {
				// TODO more detailed error handling IOex
				e.printStackTrace();
			}

		}

		System.out.println("Searching for the following opcodes:");
		//System.out.println(rawOpCodesList.toString());
	}

	public LinkedHashMap<String, Integer> countOpCodes(boolean flag) {

		int count = 0;
		int value = 0;
		double total = 0;
		lineOut = null;// tried this to fix multiplication

		// linked hash map to store the opcode with count and preserve order of
		// keys
		LinkedHashMap<String, Integer> counterMap = new LinkedHashMap<String, Integer>();

		System.out.println("Building totals array");

		// add all raw opcodes to the hash map with 0 values
		for (int j = 0; j < rawOpCodesList.size(); j++) {
			counterMap.put(rawOpCodesList.get(j), value);
		}
		// iterating through lines- the raw data file array
		Iterator<String> it = lines.iterator();

		while (it.hasNext()) {
			lineOut = it.next();
			// System.out.println("line out: "+lineOut);
			// increment count for each opcode and add to map value
			for (String t : rawOpCodesList) {

				// if the string contains the opcode

				if (lineOut.contains(t)) {
					count = counterMap.get(t);

					// increase count by one
					counterMap.put(t, count + 1);

					total++;

				}
			}

		}
		System.out.println("Total= " + total);
		writeToFile(counterMap, total, flag);
		return counterMap;
		// counterMap.clear();

	}

	public void writeToFile(LinkedHashMap<String, Integer> counterMap, double total, boolean flag) {

		this.total = total;

		for (String key : counterMap.keySet()) {
			//System.out.printf("%-10s", key);

		}

		System.out.println("");

		/*if (flag == false) {
			file = new File("C:\\Goodware_programSlices_1000\\malware_1000.csv");
		} else {
			file = new File("C:\\Goodware_programSlices_1000\\malware_1000_noZeroes.csv");
		}*/

		//file = new File("C:\\Goodware_programSlices_1000\\malware_1000.csv");
		try {

			// if doesnt exist, create
			if (!file.exists()) {
				file.createNewFile();
				// and write the headers once
				writeHeaders(flag);
			}
			// if exists, append data to file
			FileWriter fileWriter = new FileWriter(file, true);
			// buffered writer for efficiency with multiple files
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			Iterator<Entry<String, Integer>> counterIt = counterMap.entrySet().iterator();
			bufferedWriter.write(filename+",");//added to include filename
			// iterate through map
			while (counterIt.hasNext()) {

				Entry<String, Integer> pairs = counterIt.next();
				//System.out.println("Value is" + pairs.getValue());
				bufferedWriter.write(pairs.getValue() + ",");
			}
			bufferedWriter.append(System.getProperty("line.separator"));
			// bufferedWriter.flush();
			// fileWriter.close();
			bufferedWriter.close();// if you remove this close statement,
									// nothing writes to the file

		} catch (IOException e) {
			e.printStackTrace();

		}

		System.out.println("CSV file written");

		// TwoDArray.arrayBuilder(counterMap, total);

	}

}
