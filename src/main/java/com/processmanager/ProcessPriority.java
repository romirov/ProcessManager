package com.processmanager;

/**
 * The class is for setting priorities for processes.
 * @author hanza
 *
 */
public enum ProcessPriority {
	LOW (1),
	MEDIUM (5),
	HIGH (10);
	
	private int score;
	
	ProcessPriority(int score) {
		this.score = score;
	}
	
	public int getScore() {
	       return this.score;
	}

	@Override
	public String toString() {
		return "ProcessPriority{" + "score=" + this.score + "}";
	}
}
