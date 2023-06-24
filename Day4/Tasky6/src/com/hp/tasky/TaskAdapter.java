package com.hp.tasky;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Task> {

	public TaskAdapter(Context context, List<Task> tasks) {
		super(context, -1, tasks);
	}
	
	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Task task = getItem(position);
		
		View row = LayoutInflater.from(getContext()).inflate(R.layout.task_row, parent, false);
		CheckBox chkTask = (CheckBox) row.findViewById(R.id.chkTask);
		TextView txtDueDate = (TextView) row.findViewById(R.id.txtDueDate);
		
		SimpleDateFormat formatter = new SimpleDateFormat("d/M/y", Locale.getDefault());
		txtDueDate.setText(formatter.format(task.getDueDate()));
		if (task.getDueDate().before(new Date())) {
			txtDueDate.setTextColor(Color.RED);
		}
		
		chkTask.setText(task.getTitle());
		chkTask.setChecked(task.isCompleted());
		updateStrikeThrough(chkTask, task);
		chkTask.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton view, boolean isChecked) {
				task.setCompleted(isChecked);
				updateStrikeThrough(view, task);
			}
		});
		
		return row;
	}
	
	private void updateStrikeThrough(CompoundButton view, Task task) {
		if (task.isCompleted()) {
			view.setPaintFlags(view.getPaintFlags() | TextPaint.STRIKE_THRU_TEXT_FLAG);
		} else {
			view.setPaintFlags(view.getPaintFlags() & (~TextPaint.STRIKE_THRU_TEXT_FLAG));
		}

	}
	
}
