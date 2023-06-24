package com.hp.tasky;

public class Task {

	private String title;
	private boolean completed;
	
	public Task(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		if (completed) {
			return "[V] " + title;
		}
		return title;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
}
