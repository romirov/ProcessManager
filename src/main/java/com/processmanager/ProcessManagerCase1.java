package com.processmanager;

/**
 * A class that implements the functionality of the manager's process.
 * It manages processes inside the operating system.
 * @author hanza
 *
 */
public class ProcessManagerCase1 extends ProcessManager {
	public ProcessManagerCase1(int numberOfProcess) {
		super(numberOfProcess);
	}

	/**
	 * Add new processes until the limit
	 * @param process - is the process added to Process Manager
	 */
	@Override
	public void add(MyProcess process) {
		if(super.getListProcesses().size() < super.getMAX_PROCESSES()) {
			super.getListProcesses().add(process);
			System.out.println("Add new process with PID " + process.getPID());
		} else {
			process.kill();
			System.out.println("ERROR: You cannot add a new process. "
					+ "The active process limit has been reached.");
		}
	}
}
