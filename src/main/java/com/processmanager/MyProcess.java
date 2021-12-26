package com.processmanager;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MyProcess {
	private final long PID;
	private ProcessPriority priority;
	private static Logger logger;
	
	public MyProcess(){
		logger = Logger.getLogger(MyProcess.class.getName());
		ProcessBuilder builder = new ProcessBuilder("nice", "-n", "1","vim");
		Process process = null;
		try {
			process = builder.start();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		this.PID = process.pid();
		this.priority = ProcessPriority.LOW;
	}
	
	public MyProcess(ProcessPriority priority){
		logger = Logger.getLogger(MyProcess.class.getName());
		ProcessBuilder builder = new ProcessBuilder("nice", "-n", priority.toString(), "vim");
		Process process = null;
		try {
			process = builder.start();
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		this.PID = process.pid();
		this.priority = priority;
	}
	
	public long getPID() {
		return PID;
	}
	
	public ProcessPriority getPriority() {
		return priority;
	}

	public void setPriority(ProcessPriority priority) {
		this.priority = priority;
		try {
			Runtime.getRuntime().exec("renice -n " + priority.toString() + "-p" + Long.toString(PID));
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		logger.log(Level.INFO, "Process with pid " + Long.toString(PID) + " is set to priority " + priority.toString());
	}

	public void kill() {
		Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(PID);
		optionalProcessHandle.ifPresent(processHandle -> processHandle.destroy());
	}
}
