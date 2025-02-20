//Copyright (C) Queen's University of Belfast 2015-2022. 
//This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.

package opcodeAnalyser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Runner {

	public static ArrayList<String> sourceFilesArray;
	public static String opcodelist = "../fullRawOpCodeList.txt";

	public static void main(String[] args) {

		directories();
		sourceFilesIt();

		buildShortOpcodeList(sourceFilesArray);
		//countWithShortOpcodeList(sourceFilesArray);

	}

	/**
	 * takes input for directories, checks, scrapes and builds arraylist of
	 * paths as strings
	 */
	public static void directories() {

		String dirToScrape = null;
		// scrape and check dirs for files
		AnalysisSourceFiles sourceFiles = new AnalysisSourceFiles();
		dirToScrape = sourceFiles.getSourceFiles();
		// build array list from file paths
		sourceFilesArray = new ArrayList<String>(Arrays.asList(sourceFiles.deriveSourceFiles(dirToScrape)));

	}

	/**
	 * Iterates through sourceFilesArray and supplies next file to parser
	 */
	public static String sourceFilesIt() {

		String file = null;

		Iterator<String> it = sourceFilesArray.iterator();

		while (it.hasNext()) {
			file = it.next();
			//System.out.println("the file is: " + file);
		}
		return file;

	}

	// this isn't used
	public static void writeToHeaders(boolean flag) {
		Parser p = new Parser();
		p.writeHeaders(flag);

	}

	/**
	 * parses next file from source files array for opcodes on list
	 */
	public static void buildShortOpcodeList(ArrayList<String> filesToParse) {

		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

		for (int i = 0; i < filesToParse.size(); i++) {
			String filename = filesToParse.get(i);
			Parser p2 = new Parser(filename);
			System.out.println("\nFile: " + filename);
			p2.fileToArray(filename);
			p2.readRawOpCodes(opcodelist);
			//p2.writeHeaders(true);
			map = p2.countOpCodes(false);// false to allow zeroes
			System.out.println("\nRawopcode list size: " + Parser.rawOpCodesList.size());

		}

		Filter f = new Filter();

		f.postFilterOpcodeFileWriter(f.filter(map));

	}

	/**
	 * parses next file from source files array for opcodes on list
	 */
	public static void countWithShortOpcodeList(ArrayList<String> filesToParse) {

		// LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

		for (int i = 0; i < filesToParse.size(); i++) {
			String filename = filesToParse.get(i);
			Parser p2 = new Parser(filename);
			System.out.println("\nFile: " + filename);
			p2.fileToArray(filename);
			p2.readRawOpCodes("postFilterOpCodes.txt");
			// p2.writeHeaders();
			p2.countOpCodes(true);// to disallow zeroes
			System.out.println("\nRawopcode list size: " + Parser.rawOpCodesList.size());

		}

	}

}
