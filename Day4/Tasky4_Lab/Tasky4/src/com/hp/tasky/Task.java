package com.hp.tasky;

import java.util.Date;

public class Task {

	private String title;
	private boolean completed;
	private Date dueDate;
	
	public Task(String title) {
		this.title = title;
		this.dueDate = new Date(); // now
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void snooze24Hours() {
		dueDate = new Date(dueDate.getTime() + 24*60*60*1000);
	}
	
}
