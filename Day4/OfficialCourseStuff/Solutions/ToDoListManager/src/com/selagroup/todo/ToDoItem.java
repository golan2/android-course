package com.selagroup.todo;

import java.io.Serializable;
import java.util.Date;

import android.content.Context;
import android.view.ContextMenu;

public class ToDoItem implements Serializable {

	private static final long serialVersionUID = 3499766437948020182L;

	private String text;
	private Date creationDate;
	private Date dueDate;
	
	public String getText() {
		return text;
	}
	
	public void setText(String txt) {
		text = txt;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date due) {
		dueDate = due;
	}
	
	public ToDoItem(String txt) {
		this(txt, new Date(System.currentTimeMillis()));
	}
	
	public ToDoItem(String txt, Date created) {
		text = txt;
		creationDate = created;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public void buildContextMenu(ContextMenu menu) {
	}
	
	public boolean contextMenuClicked(Context context, int itemId) {
		return false;
	}
	
}
