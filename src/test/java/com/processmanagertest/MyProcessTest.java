package com.processmanagertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.*;
import com.processmanager.MyProcess;
import com.processmanager.ProcessPriority;

public class MyProcessTest {
	private static MyProcess processWithDefaultPriority;
	private static MyProcess processWithSetPriority;
	
	@BeforeAll
	public static void init() {
		processWithDefaultPriority = new MyProcess("vim");
		processWithSetPriority = new MyProcess("vim",ProcessPriority.MAX_PRIORITY);
	}
	
	@Test
	public void getPIDTest() {
		Assumptions.assumeTrue(processWithDefaultPriority.getPID() > 0);
		Assumptions.assumeFalse(processWithSetPriority.getPID() <= 0);
	}
	
	@Test
	public void setPriorityTest() {
		processWithDefaultPriority.setPriority(ProcessPriority.NORM_PRIORITY);
		Assumptions.assumeTrue(processWithDefaultPriority.getPriority() == ProcessPriority.NORM_PRIORITY);
	}
	
	@Test
	public void getPriorityTest() {
		Assumptions.assumeFalse(processWithDefaultPriority.getPriority() == ProcessPriority.MIN_PRIORITY);
		Assumptions.assumeTrue(processWithSetPriority.getPriority() == ProcessPriority.MAX_PRIORITY);
	}
	
	@Test
	public void killTest() {
		processWithDefaultPriority.kill();
		processWithSetPriority.kill();
		
		Assumptions.assumeTrue(checkAlivedProcess(processWithDefaultPriority) == false);
		Assumptions.assumeTrue(checkAlivedProcess(processWithSetPriority) == false);
	}
	
	/**
	 * Checks if the process is alive
	 * @param process
	 * @return true if process is alive, false otherwise
	 */
	private boolean checkAlivedProcess(MyProcess process) {
		Runtime r = Runtime.getRuntime();
		String resultString = "";
		Process p = null;
		boolean isAlive = false;
		try {
			p = r.exec("ps -p " + process.getPID());
			p.waitFor();
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			resultString = b.readLine();
			isAlive = resultString.contains(Long.toString(process.getPID()));
		} catch (IOException|InterruptedException e) {
			e.printStackTrace();
		} 
		return isAlive;
	}
}
