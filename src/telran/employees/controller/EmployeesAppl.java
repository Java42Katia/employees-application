package telran.employees.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import telran.employees.services.*;
import telran.view.*;

public class EmployeesAppl {

	public static void main(String[] args) {
		InputOutput io = new ConsoleInputOutput();
		if (args.length < 1) {
			io.writeObjectLine("Usage - argument should contain configuration file name");
			return;
		}
		// Configuration file contains text like employeesDataFile=employees.data
		// Apply BufferedReader for reading configuration
		String fileName = null;
		try {
			fileName = getFileName(args[0]);
		} catch (IOException e) {
			io.writeObjectLine(e.getMessage());
			return;
		}
		EmployeesMethods employeesMethods = new EmployeesMethodsMapsImpl(fileName);
		employeesMethods.restore();
		HashSet<String> departments = new HashSet<>(Arrays.asList("QA", "Development", "HR", "Management"));
		Menu menu = new Menu("Employees Application", EmployeeActions.getActionItems(employeesMethods, departments));
		menu.perform(io);

	}

	private static String getFileName(String configFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(configFile));
		String line = reader.readLine();
		reader.close();
		return line.split("=")[1];
	}

}
