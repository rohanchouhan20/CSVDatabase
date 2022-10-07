package com.csvdb.exception;

public class Counter {

	public int processedLines;
	
	public int getProcessedLines() {
		return processedLines;
	}

	public void setProcessedLines(int processedLines) {
		this.processedLines = processedLines;
	}

	public int getSkippedLines() {
		return skippedLines;
	}

	public void setSkippedLines(int skippedLines) {
		this.skippedLines = skippedLines;
	}

	public int skippedLines;
}
