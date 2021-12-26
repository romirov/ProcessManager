package com.processmanager;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.List;

public class ProcessManager {
	
	private Map<Long, MyProcess> listProcesses;
	private static Logger logger;
	private final int MAX_PROCESSES;
	
	public  ProcessManager() {
		this.listProcesses = new HashMap<Long, MyProcess>();
		logger = Logger.getLogger(ProcessManager.class.getName());
		MAX_PROCESSES = 3;
	}
	
	public  ProcessManager(int numberOfProcesses) {
		this.listProcesses = new HashMap<Long, MyProcess>();
		logger = Logger.getLogger(ProcessManager.class.getName());
		MAX_PROCESSES = numberOfProcesses;
	}

	/**
	 * Create new process
	 */
	public void add() {
		MyProcess process = new MyProcess();
		this.listProcesses.put(process.getPID(), process);
		logger.log(Level.INFO, "Create new process with PID " + process.getPID());
	}
	
	/**
	 * Create new process
	 */
	public void add(int z) {
		MyProcess process = new MyProcess();
		this.listProcesses.put(process.getPID(), process);
		logger.log(Level.INFO, "Create new process with PID " + process.getPID());
	}
	
	/**
	 * Lists active processes
	 */
	public void list() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Running processes: \n");
		this.listProcesses.forEach((pid, process) -> stringBuilder.append(
				"Activ process{ PID: " + process.getPID()
				+ "Priority" + process.getPriority() + "}\n"));
		logger.log(Level.INFO, stringBuilder.toString());
	}
	
	
	public void killProcess(Long pid) {
		if(listProcesses.containsKey(pid)) {
			MyProcess process = this.listProcesses.get(pid);
			process.kill();
			listProcesses.remove(pid);
			logger.log(Level.INFO, "Kill process with PID " + process.getPID());
		} else {
			logger.log(Level.SEVERE, "Process with PID " + pid + " not found!");
		}
	}
	
	public void killGroupProcesses(List<Long> pids) {
		pids.forEach(pid -> killProcess(pid));
	}
	
	public void killAllProcesses() {
		if(listProcesses.size() > 0) {
			listProcesses.forEach((pid, process) -> process.kill());
			listProcesses.clear();
			logger.log(Level.INFO, "All active processes have been killed");
		} else {
			logger.log(Level.SEVERE, "No active processes!");
		}
	}
}
