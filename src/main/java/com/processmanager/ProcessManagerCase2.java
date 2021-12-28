package com.processmanager;

public class ProcessManagerCase2 extends ProcessManager {
	
	public  ProcessManagerCase2 (int numberOfProcess) {
		super(numberOfProcess);
	}

	/**
	 * Add new process, oldest processes must be removed to add a new process
	 * @param processesName - list of process names to create
	 */
	@Override
	public void add(MyProcess process) {
		if(super.getListProcesses().size() < super.getMAX_PROCESSES()) {
			super.getListProcesses().add(process);
			System.out.println("Add new process with PID " + process.getPID());
		} else {
			System.out.println("The active process limit has been reached.");
			long pid = super.getListProcesses().get(0).getPID();
			super.kill(pid);
			super.getListProcesses().add(process);
			System.out.println("Add new process with PID " + process.getPID());
		}
	}
}
