package com.processmanager;

import java.util.Scanner;

public class ProcessManagerImp {
	private Scanner scanner;
	private ProcessManager processManager;
	private MyProcess process;

	private Scanner getScanner() {
		return scanner;
	}

	private void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	private ProcessManager getProcessManager() {
		return processManager;
	}

	private void setProcessManager(ProcessManager processManager) {
		this.processManager = processManager;
	}
	
	private MyProcess getProcess() {
		return process;
	}

	private void setProcess(MyProcess process) {
		this.process = process;
	}
	
	/**
	 * Select Case Process Manager
	 * @return case process manager(case1, case2, case3) or "" if you didn't choose anything
	 */
	private String selectCaseProcessManager() {
		if(scanner != null) {
			while(scanner.hasNextLine()) {
				String string = scanner.nextLine();
				if(string.equals("exit")) {
					return "";
				} else if(string.equals("case1") || string.equals("case2") || string.equals("case3")) {
					return string;
				} else {
					System.out.println("ERROR: Please enter the case of Process Manager.");
				}
			}
		}
		return "";
	}
	
	/**
	 * Select number of processes
	 * @return number of processes or -1 if you didn't choose anything (by default is 3)
	 */
	private int selectNumberOfProcess() {
		if(scanner != null) {
			while(scanner.hasNextLine()) {
				String string = scanner.nextLine();
				if(string.equals("exit")) {
					return -1;
				} else if(string.equals("")) {
					return 3;
				} else if(string.substring(0, string.length()).matches("^[\\d]+")) {
					return Integer.valueOf(string);
				} else {
					System.out.println("ERROR: Please enter the number of active processes.");
				}
			}
		}
		return -1;
	}
	
	/**
	 * Get commands for the process manager from command line
	 */
	private void selectCommand() {
		if(scanner != null) {
			while(scanner.hasNextLine()) {
				String commandString = scanner.nextLine();
				if(commandString.equals("help")) {
					helpFunc();
				} else if(commandString.equals("exit")) {
					break;
				} else {
					selectMainProcessManagerCommand(commandString);
				}
				System.out.println("Please enter command or help:");
			}
		}
	}
	
	/**
	 * Сhecks a string for a match with the main Process Manager commands with arguments: add, kill, list
	 * @param command
	 */
	private void selectMainProcessManagerCommand(String command) {
		if(stringMatchCheck(command)) {
			if(command.matches("^add")) {
				MyProcess process = new MyProcess("vim");
				System.out.println("Create new process with PID " + process.getPID());
				processManager.add(process);
			} else if(command.matches("^add -min")) {
				MyProcess process = new MyProcess("vim", ProcessPriority.MIN_PRIORITY);
				System.out.println("Create new process with PID " + process.getPID() + " and priority " + ProcessPriority.MIN_PRIORITY);
				processManager.add(process);
			} else if(command.matches("^add -norm")) {
				MyProcess process = new MyProcess("vim", ProcessPriority.NORM_PRIORITY);
				System.out.println("Create new process with PID " + process.getPID() + " and priority " + ProcessPriority.NORM_PRIORITY);
				processManager.add(process);
			} else if(command.matches("^add -max")) {
				MyProcess process = new MyProcess("vim", ProcessPriority.MAX_PRIORITY);
				System.out.println("Create new process with PID " + process.getPID() + " and priority " + ProcessPriority.MAX_PRIORITY);
				processManager.add(process);
			} else if(command.matches("^list -t")) {
				processManager.listTime();
			} else if(command.matches("^list -p")) {
				processManager.listPid();
			} else if(command.matches("^list -pt")) {
				processManager.listPriority();
			} else if(command.matches("kill -p [\\d]+")) {
				long pid = Long.valueOf(command.split(" ")[2]);
				processManager.kill(pid);
			} else if(command.matches("^kill -min")) {
				processManager.killGroup(ProcessPriority.MIN_PRIORITY);
			} else if(command.matches("^kill -norm")) {
				processManager.killGroup(ProcessPriority.NORM_PRIORITY);
			} else if(command.matches("^kill -max")) {
				processManager.killGroup(ProcessPriority.MAX_PRIORITY);
			} else if(command.matches("^kill -a")) {
				processManager.killAll();
			} else {
				System.out.println("ERROR: Command not found!");
			}
		}
	}
	
	/**
	 * Сhecks strings against commands pattern
	 * @param string from command line
	 * @return returns true if the string matches the command pattern and false otherwise
	 */
	private boolean stringMatchCheck(String command) {
		if(command.matches("[\\p{Lower}]+")) {
			return true;
		} else if(command.matches("[\\p{Lower}]+ -\\p{Lower}+")) {
			return true;
		} else if(command.matches("[\\p{Lower}]+ -\\p{Lower}+ [\\d]+")) {
			return true;
		}
		return false;
	}

	/**
	 * Describes the functionality of ProcessManager
	 */
	private void helpFunc() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ProcessManager is a program designed to manage"
				+ " processes within the operating system.\n"
				+ "Possible priorities:\n"
				+ "- MIN_PRIORITY - 10\n"
				+ "- NORM_PRIORITY - 5\n"
				+ "- MAX_PRIORITY - 1\n"
				+ "Options: \n");
		stringBuilder.append("help - displays a description of the commands supported by this program.\n");
		stringBuilder.append("add - new processes are added until the limit is exceeded,"
				+ " otherwise the new process is not accepted.\n");
		stringBuilder.append("add -f - new processes are added, and if the limit is exceeded,"
				+ " then the Process Manager must kill the oldest processes (First-In, First-Out).\n");
		stringBuilder.append("add process_priority - new processes are added and if a new process\n"
				+ "takes precedence over any existing one, then the oldest process from\n"
				+ "the lowest priority. process_priority is the selected priority of the process. "
				+ "Otherwise, we don't add a process. One of the following values ​​must be entered: -min, -norm, -max."
				+ "Example:\nadd -min\n");
		stringBuilder.append("list -t - lists active processes sorted by time.\n");
		stringBuilder.append("list -p - lists active processes sorted by PID.\n");
		stringBuilder.append("list -pt - lists active processes sorted by priority.\n");
		stringBuilder.append("kill -p PID- kills the process with process identification number."
				+ "PID is the identifier of the process in the system.\n");
		stringBuilder.append("kill process_priority - kills all processes with the selected priority. "
				+ "process_priority is the selected priority of the process. "
				+ "One of the following values ​​must be entered: -min, -norm, -max."
				+ "Example:\nkill -min");
		stringBuilder.append("kill -a - kills all processes.\n");
		stringBuilder.append("exit - exit the program");
		System.out.println(stringBuilder.toString());
	}
	
	public static void main(String[] args) {
		ProcessManagerImp processManagerImp = new ProcessManagerImp(); 
		processManagerImp.setScanner(new Scanner(System.in));
		System.out.println("Select Case Process Manager(enter case1 or case2 or case3)");
		String caseString = processManagerImp.selectCaseProcessManager();
		System.out.println("Enter the number of active processes that the Process Manager should manage (default 3)or 'enter' or exit:");
		int numberOfProcesses = processManagerImp.selectNumberOfProcess();

		if(!caseString.isEmpty() && numberOfProcesses > 0) {
			switch (caseString) {
				case "case1": {
					processManagerImp.setProcessManager(new ProcessManagerCase1(numberOfProcesses));
					break;
				}
				case "case2": {
					processManagerImp.setProcessManager(new ProcessManagerCase2(numberOfProcesses));
					break;
				}
				case "case3": {
					processManagerImp.setProcessManager(new ProcessManagerCase3(numberOfProcesses));
					break;
				}
			}
			System.out.println("Please enter command or help:");
			processManagerImp.selectCommand();
		}
		processManagerImp.getScanner().close();
	}
}
