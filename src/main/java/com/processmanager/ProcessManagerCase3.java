package com.processmanager;

import java.util.Iterator;

public class ProcessManagerCase3 extends ProcessManager {
	
	public  ProcessManagerCase3(int numberOfProcess) {
		super(numberOfProcess);
	}
	
	/**
	 * Add new process. If the new process takes precedence over any existing one,
	 * then the oldest process from the lowest priority.
	 * @param processesName - list of process names to create
	 * @param priority - priority for the process being created
	 */
	@Override
	public void add(MyProcess myProcess) {
		if(super.getListProcesses().size() < super.getMAX_PROCESSES()) {
			super.getListProcesses().add(myProcess);
			System.out.println("Add new process with PID " + myProcess.getPID());
		} else {
			boolean foundProcessWithHighestPriority = findProcessWithHighestPriority(myProcess);
			
			if(foundProcessWithHighestPriority == false) {
				MyProcess processWithMinPriority = getProcessWithMinPriority();
				deleteOldestProcessWithMinPriority(processWithMinPriority);
				super.getListProcesses().add(myProcess);
				System.out.println("Add new process with PID " + myProcess.getPID());
			} else {
				myProcess.kill();
				System.out.println("ERROR: You cannot create a new process. "
						+ "The active process limit has been reached and no processes with priority less than new.");
			}
		}
	}
	
	/**
	 * Looks for a process with a priority higher than the priority of the process specified in the method parameter
	 * @param myProcess - a process whose priority is compared with the priorities of processes in the list of active processes
	 * @return true - if there is a high priority process in the list, false otherwise
	 */
	private boolean findProcessWithHighestPriority(MyProcess myProcess) {
		return super.getListProcesses().stream().anyMatch(process -> 
		process.getPriority().getScore() < myProcess.getPriority().getScore());
	}
	
	/**
	 * Returns the process with the lowest priority
	 * @return the process with the lowest priority
	 */
	private MyProcess getProcessWithMinPriority() {
		return super.getListProcesses().stream().min((process1, process2) -> 
		process1.getPriority().compareTo(process2.getPriority())).get();
	}
	
	/**
	 * Delete oldest process with the very minimum priority
	 * @param processWithMinPriority - oldest process with the very minimum priority
	 */
	private void deleteOldestProcessWithMinPriority(MyProcess processWithMinPriority) {
		Iterator<MyProcess> processIterator = super.getListProcesses().iterator();
		while (processIterator.hasNext()) {
			MyProcess process = processIterator.next();
			if(process.getPriority() == processWithMinPriority.getPriority()) {
				System.out.println("Kill process with PID " + process.getPID() + " priority " + process.getPriority());
				process.kill();
				processIterator.remove();
				break;
			} 
		}
	}
}
