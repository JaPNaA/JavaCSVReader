package csv;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.TreeMap;

/**
 * A simple, but inefficient implementation of a CSV reader.
 * 
 * There are better ones online.
 *   - Things that could be sped up:
 *     - Parse while reading input
 *     - Don't count, and just append
 * 
 * @author japnaa
 *
 */

public class CSVTable {
	private String[] keys;
	private String[][] values;
	private TreeMap<String, Integer> keyIndexMap;

	public CSVTable(String fileName) {
		keyIndexMap = new TreeMap<>();

		parseString(readFile(fileName));
	}
	
	public Row getRow(int index) {
		return new Row(values[index], this);
	}
	
	public Row getRowByValue(String key, String value) {
		int index = getKeyIndex(key);
		
		if (index < 0) {
			return null;
		}
		
		for (String[] row : values) {
			if (row[index].equals(value)) {
				return new Row(row, this);
			}
		}
		
		return null;
	}

	private int getKeyIndex(String key) {
		try {
			return keyIndexMap.get(key);
		} catch (NullPointerException err) {
			return -1;
		}
	}
	
	private String readFile(String fileName) {
		FileInputStream in = createFileInputStream(fileName);
		StringBuilder builder = new StringBuilder();

		appendStreamToStringBuilder(in, builder);

		return builder.toString();
	}

	private FileInputStream createFileInputStream(String file) {
		try {
			return new FileInputStream(file);
		} catch (Exception err) {
			err.printStackTrace(System.err);
			throw new Error("Error initalizing FileInputStream");
		}
	}

	private void appendStreamToStringBuilder(FileInputStream in, StringBuilder builder) {
		try {
			while (true) {
				int charRead = in.read();

				if (charRead == -1) {
					break;
				}

				builder.append((char) charRead);
			}
		} catch (IOException err) {
			err.printStackTrace(System.err);
			throw new Error("Failed to read file");
		} finally {
			// the 'finally' block could be new to you, anything
			// in these braces will ALWAYS run.
			// ONLY with a few exceptions: System.exit, terminating
			// the Java process from Task Manager, a blue screen of
			// death, an electricity outage, or being struck my a
			// cosmic ray.
			closeStream(in);
		}
	}

	private void closeStream(FileInputStream s) {
		try {
			s.close();
		} catch (IOException err) {
			err.printStackTrace(System.err);
			throw new Error("Failed to close FileInputStream");
		}
	}

	private void parseString(String input) {
		int firstNewline = input.indexOf("\n");
		String header = input.substring(0, firstNewline);
		String body = input.substring(firstNewline + 1, input.length() - 1);
		
		parseHeader(header);
		parseBody(body);
	}
	
	private void parseHeader(String header) {
		keys = header.split(",");
		
		for (int i = 0; i < keys.length; i++) {
			keyIndexMap.put(keys[i], i);
		}
	}
	
	private void parseBody(String body) {
		String[] rows = body.split("\n");
		String[] firstCol = rows[0].split(",");
		
		values = new String[rows.length][firstCol.length];
		
		values[0] = firstCol;
		
		for (int i = 1; i < rows.length; i++) {
			values[i] = rows[i].split(",");
		}
	}
}
