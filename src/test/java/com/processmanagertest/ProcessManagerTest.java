package com.processmanagertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.processmanager.MyProcess;
import com.processmanager.ProcessManager;
import com.processmanager.ProcessManagerCase1;
import com.processmanager.ProcessManagerCase2;
import com.processmanager.ProcessManagerCase3;
import com.processmanager.ProcessPriority;

public class ProcessManagerTest {
	private static ProcessManager pm1;// for case1
	private static ProcessManager pm2;// for case2
	private static ProcessManager pm3;// for case3
	
	@BeforeAll
	public static void init() {
		pm1 = new ProcessManagerCase1(1);
		pm2 = new ProcessManagerCase2(3);
		pm3 = new ProcessManagerCase3(3);
	}
	
	/**
	 * Check case1, then add new processes until the limit
	 */
	@Test
	public void addCase1Test() {
		MyProcess p1 = new MyProcess("vim");
		MyProcess p2 = new MyProcess("vim");
		pm1.add(p1);
		pm1.add(p2);
		Assertions.assertFalse(checkAlivedProcess(p2));
	}
	
	/**
	 * Check case2, then add new process, oldest processes must be removed to add a new process
	 */
	@Test
	public void addCase2Test() {
		MyProcess p1 = new MyProcess("vim");
		MyProcess p2 = new MyProcess("vim");
		MyProcess p3 = new MyProcess("vim");
		MyProcess p4 = new MyProcess("vim");
		pm2.add(p1);
		pm2.add(p2);
		pm2.add(p3);
		pm2.add(p4);
		
		Assertions.assertFalse(checkAlivedProcess(p1));
		Assertions.assertEquals(pm2.getListProcesses().size(), 3);
	}
	
	/**
	 * Check case3, then add new process. If the new process takes precedence over any existing one,
	 * then the oldest process from the lowest priority.
	 */
	@Test
	public void addCase3Test() {
		MyProcess p1 = new MyProcess("vim", ProcessPriority.NORM_PRIORITY);
		MyProcess p2 = new MyProcess("vim", ProcessPriority.MIN_PRIORITY);
		MyProcess p3 = new MyProcess("vim", ProcessPriority.MIN_PRIORITY);
		MyProcess p4 = new MyProcess("vim", ProcessPriority.MAX_PRIORITY);
		pm3.add(p1);
		pm3.add(p2);
		pm3.add(p3);
		pm3.add(p4);
		Assertions.assertFalse(checkAlivedProcess(p2));
		Assertions.assertEquals(pm3.getListProcesses().size(), 3);
	}
	
	@Test
	public void killTest() {
		MyProcess p1 = new MyProcess("vim");
		MyProcess p2 = new MyProcess("vim");
		MyProcess p3 = new MyProcess("vim");

		pm2.add(p1);
		pm2.add(p2);
		pm2.add(p3);
		
		pm2.kill(p2.getPID());
		pm2.kill(p3.getPID());
		
		Assertions.assertFalse(checkAlivedProcess(p2));
		Assertions.assertFalse(checkAlivedProcess(p3));
		Assertions.assertEquals(pm2.getListProcesses().size(), 1);
	}
	
	@Test
	public void killGroup() {
		MyProcess p1 = new MyProcess("vim", ProcessPriority.MIN_PRIORITY);
		MyProcess p2 = new MyProcess("vim", ProcessPriority.NORM_PRIORITY);
		MyProcess p3 = new MyProcess("vim", ProcessPriority.MIN_PRIORITY);
	
		pm3.add(p1);
		pm3.add(p2);
		pm3.add(p3);
		
		pm3.killGroup(ProcessPriority.MIN_PRIORITY);
		
		Assertions.assertFalse(checkAlivedProcess(p1));
		Assertions.assertFalse(checkAlivedProcess(p3));
		Assertions.assertEquals(pm3.getListProcesses().size(), 1);
	}
	
	@Test
	public void killAll() {
		MyProcess p1 = new MyProcess("vim");
		MyProcess p2 = new MyProcess("vim");
		MyProcess p3 = new MyProcess("vim");

		pm2.add(p1);
		pm2.add(p2);
		pm2.add(p3);
		
		pm2.killAll();
		
		Assertions.assertFalse(checkAlivedProcess(p1));
		Assertions.assertFalse(checkAlivedProcess(p2));
		Assertions.assertFalse(checkAlivedProcess(p3));
		Assertions.assertEquals(pm2.getListProcesses().size(), 0);
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
