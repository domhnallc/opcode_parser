# Opcode Analyser

Copyright (C) Queen's University of Belfast 2015-2022. 
This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.

**Author**: Domhnall Carlin  
**Email**: D.Carlin at qub.ac.uk

# About
This program takes a folder of runtrace files and parses them for instruction sets specified. It then writes out a CSV file of the count of each instruction in the trace, with one instruction per column and one runtrace per row.  This is used for dynamic opcode analysis research at Queen's University Belfast. See https://doi.org/10.1109/ACCESS.2017.2749538 for further details.

# Requirements
Working Java installation (JRE and SDK) above Java 8.

# Usage

1. Set `opcodelist` in Runner.java to whatever list of words you wish to count. The default is `/<path>/<to>/<folder>/opcodeAnalyser/fullRawOpCodeList.txt`, which is the x86-64 instruction set.
2. Set `file` in Parser.java to the csv file you'd like to write out to. It will be created if it doesn't exist and be appended to if it does.  The default is `/<path>/<to>/<folder>/opcodeAnalyser/sample.csv`.

If running from the commandline:  

3. Compile the Java source files `javac /<path>/<to>/<folder>/opcodeAnalyser/src/opcodeAnalyser/*.java`.
4. Move to the `/<path>/<to>/<folder>/opcodeAnalyser/src/` folder.
5. Run `java opcodeAnalyser.Runner`.
6. Enter the folder containing the file(s) to be parsed.
7. The analyser will check the directory and all of the files are valid paths, then proceed to count all occurrences of the list specified in 1. in all files in 6. and write this one line at a time (following headers) to the CSV specified in 2.

# Example
The sample folder contains a runtrace file.  To run the example, follow the steps above (keeping defaults in steps 1 and 2).


