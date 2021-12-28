package com.processmanager;

import java.io.IOException;
import java.util.Optional;

/**
 * Сlass that implements the process inside the operating system
 * @author hanza
 *
 */
public final class MyProcess {
	private final long PID;//identifier of the process in the system
	private ProcessPriority priority;//priority of the process
	
	public MyProcess(String processName){
		ProcessBuilder builder = new ProcessBuilder("nice", "-n", ProcessPriority.MIN_PRIORITY.toString(), processName);
		Process process = null;
		try {
			process = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.PID = process.pid();
		this.priority = ProcessPriority.MIN_PRIORITY;
	}
	
	public MyProcess(String processName, ProcessPriority priority){
		ProcessBuilder builder = new ProcessBuilder("nice", "-n", priority.toString(), processName);
		Process process = null;
		try {
			process = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			Runtime.getRuntime().exec("renice -n " + priority.toString() + "-p" + Long.toString(PID));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.priority = priority;
		System.out.println("Process with pid " + Long.toString(PID) + " is set to priority " + priority.toString());
	}

	public void kill() {
		Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(PID);
		optionalProcessHandle.ifPresent(processHandle -> processHandle.destroy());
	}
}
