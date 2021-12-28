package com.processmanager;

/**
 * The enum is for setting priorities for processes.
 * @author hanza
 *
 */
public enum ProcessPriority {
	MIN_PRIORITY (10),
	NORM_PRIORITY (5),
	MAX_PRIORITY (1);
	
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
