package com.selagroup.todo;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

	private List<ToDoItem> items;
	
	public ToDoItemAdapter(Context context, List<ToDoItem> itms) {
		super(context, R.layout.todoitem, itms);
		items = itms;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.todoitem, null);
		}
		TextView task = (TextView)convertView.findViewById(R.id.taskTextView);
		TextView date = (TextView)convertView.findViewById(R.id.dateTextView);
		ToDoItem toDoItem = items.get(position);
		task.setText(toDoItem.getText());
		SimpleDateFormat format = new SimpleDateFormat("d/M/y H:m");
		date.setText(format.format(toDoItem.getCreationDate()));
		return convertView;
	}

}
