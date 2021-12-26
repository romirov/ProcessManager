package com.processmanager;

import java.util.Scanner;
import java.util.logging.Logger;

public class ProcessManagerImp {
	private static Logger logger = Logger.getLogger(ProcessManagerImp.class.getName());
	
	public static void main(String[] args) {
		ProcessManager processManager = new ProcessManager();
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			switch (scanner.next()) {
			case "help": helpFunc();
			default:
				throw new IllegalArgumentException("Unexpected value: " + scanner.);
			}
		}
	}
	
	private static void helpFunc() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ProcessManager is a program designed to manage"
				+ " processes within the operating system.\nOptions: \n");
		stringBuilder.append("help - displays a description of the commands supported by this program.\n");
		stringBuilder.append("add - creates a process.\n");
		stringBuilder.append("list - lists active processes.\n");
		stringBuilder.append("kill PID - kills the process. "
				+ "PID is the identifier of the process in the system.\n");
		stringBuilder.append("kill group process_priority - kills the process. "
				+ "PID is the identifier of the process in the system.\n");
		stringBuilder.append("kill all - kills all processes.\n");
		System.out.println(stringBuilder.toString());
	}

}
