package com.processmanager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ProcessManager implements ProcessManagerCommands{
	private List<MyProcess> listProcesses;//contains a list of active processes
	private final int MAX_PROCESSES;//maximum value of active processes

	public ProcessManager() {
		this.listProcesses = new ArrayList<MyProcess>();
		MAX_PROCESSES = 3;
		System.out.println("The maximum number of processes that the program can control is " + MAX_PROCESSES);
	}
	
	public  ProcessManager (int numberOfProcess) {
		this.listProcesses = new ArrayList<MyProcess>();
		MAX_PROCESSES = numberOfProcess;
		System.out.println("The maximum number of processes that the program can control is " + MAX_PROCESSES);
	}
	
	public List<MyProcess> getListProcesses() {
		return listProcesses;
	}
	
	public int getMAX_PROCESSES() {
		return MAX_PROCESSES;
	}
	/**
	 * Add new processes
	 * @param process - is the process added to Process Manager
	 */
	public abstract void add(MyProcess process);
	
	/**
	 * Lists processes sorted by time
	 */
	public void listTime() {
		StringBuilder stringBuilder = new StringBuilder();
		if(!listProcesses.isEmpty()) {
			stringBuilder.append("Running processes by time: \n");
			listProcesses.forEach(process -> stringBuilder.append("Activ process{PID: " + process.getPID()
					+ "\tPriority: " + process.getPriority() + "}\n"));
			System.out.println(stringBuilder.toString());
		} else{
			System.out.println("ERROR: No active processes!");
		}
	}
	
	/**
	 * Lists processes sorted by PID
	 */
	public void listPid() {
		StringBuilder stringBuilder = new StringBuilder();
		if(!listProcesses.isEmpty()) {
			stringBuilder.append("Running processes by PID: \n");
			Stream<MyProcess> stream = listProcesses.stream().sorted((process1, process2) -> Long.valueOf(process1.getPID()).compareTo(process2.getPID()));
			List<MyProcess> listProcessesSortedByPid = stream.collect(Collectors.toList());
			listProcessesSortedByPid.forEach(process -> stringBuilder.append("Activ process{PID: " + process.getPID()
					+ "\tPriority: " + process.getPriority() + "}\n"));
			System.out.println(stringBuilder.toString());
		} else{
			System.out.println("ERROR: No active processes!");
		}
	}
	
	/**
	 * Lists processes sorted by priority
	 */
	public void listPriority() {
		StringBuilder stringBuilder = new StringBuilder();
		if(!listProcesses.isEmpty()) {
			stringBuilder.append("Running processes by priority: \n");
			Stream<MyProcess> stream = listProcesses.stream().sorted((process1, process2) -> process2.getPriority().compareTo(process1.getPriority()));
			List<MyProcess> listProcessesSortedByPriority = stream.collect(Collectors.toList());
			listProcessesSortedByPriority.forEach(process -> stringBuilder.append("Activ process{PID: " + process.getPID()
					+ "\tPriority: " + process.getPriority() + "}\n"));
			System.out.println(stringBuilder.toString());
		} else{
			System.out.println("ERROR: No active processes!");
		}
	}
	
	/**
	 * Kill process with PID
	 * @param pid -identifier of the process in the system
	 */
	public void kill(Long pid) {
		Iterator<MyProcess> processIterator = listProcesses.iterator();
		while (processIterator.hasNext()) {
			MyProcess process = processIterator.next();
			if(process.getPID() == pid) {
				process.kill();
				processIterator.remove();
				System.out.println("Kill process with PID " + pid);
				break;
			}
		}
	}
	
	/**
	 * Kill processes with priority
	 * @param priority - priority of the process in the system
	 */
	public void killGroup(ProcessPriority priority) {
		Iterator<MyProcess> processIterator = listProcesses.iterator();
		int countKilledProcess = 0;
		while (processIterator.hasNext()) {
			MyProcess process = processIterator.next();
			if(process.getPriority() == priority) {
				process.kill();
				processIterator.remove();
				countKilledProcess ++;
				System.out.println("Kill process with priority " + priority);
			} 
		}
		if(countKilledProcess == 0) {
			System.out.println("No processes found with priority" + priority);
		}
	}
	
	/**
	 * Kill all processes
	 */
	public void killAll() {
		if(listProcesses.size() > 0) {
			listProcesses.forEach(process -> process.kill());
			listProcesses.clear();
			System.out.println("All active processes have been killed");
		} else {
			System.out.println("ERROR: No active processes!");
		}
	}
}
