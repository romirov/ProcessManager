package com.processmanager;

public interface ProcessManagerCommands {
	public void add(MyProcess process);
	public void listTime();
	public void listPid();
	public void listPriority();
	public void kill(Long pid);
	public void killGroup(ProcessPriority priority);
	public void killAll();
}
