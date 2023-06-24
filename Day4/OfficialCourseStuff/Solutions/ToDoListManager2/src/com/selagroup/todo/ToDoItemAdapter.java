package com.selagroup.todo;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ToDoItemAdapter extends SimpleCursorAdapter {
	
	private Context context;
	
	public ToDoItemAdapter(Context context, Cursor cursor) {
		super(context, R.layout.todoitem, cursor,
				new String[] { ToDoDAL.TASK_COLUMN, ToDoDAL.CREATED_COLUMN },
				new int[] { R.id.taskTextView, R.id.dateTextView });
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.todoitem, null);
		}
		TextView task = (TextView)convertView.findViewById(R.id.taskTextView);
		TextView date = (TextView)convertView.findViewById(R.id.dateTextView);
		Cursor cursor = getCursor();
		cursor.moveToPosition(position);
		ToDoItem toDoItem = ToDoDAL.fromCurrentCursorPosition(cursor);
		task.setText(toDoItem.toString());
		SimpleDateFormat format = new SimpleDateFormat("d/M/y HH:mm");
		date.setText(format.format(toDoItem.getCreationDate()));
		return convertView;
	}

}
