package test;

import csv.CSVTable;

public class Main {

	public static void main(String[] args) {
		CSVTable table = new CSVTable("src/data.csv");

		
		System.out.println("First element listed: " + table.getRow(0).get("Element"));
		
		System.out.println(
				"Element with atomic number 8: " + table.getRowByValue("AtomicNumber", "8").get("Element"));
		
		System.out.println("Krypton was discovered by: " + table.getRowByValue("Element", "Krypton").get("Discoverer"));
	}

}
