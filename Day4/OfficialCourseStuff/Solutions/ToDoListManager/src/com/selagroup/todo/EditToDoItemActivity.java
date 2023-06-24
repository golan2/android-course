package com.selagroup.todo;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class EditToDoItemActivity extends Activity {

	private ToDoItem item;
	private int position;
	private EditText taskEdit;
	private DatePicker datePicker;
	private Button okButton;
	private Button cancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_item);
		
		Intent intent = getIntent();
		item = (ToDoItem) intent.getSerializableExtra("Item");
		position = intent.getIntExtra("Position", -1);
		
		taskEdit = (EditText)findViewById(R.id.editTaskText);
		datePicker = (DatePicker)findViewById(R.id.editDueDatePicker);
		
		taskEdit.setText(item.getText());
		
		okButton = (Button)findViewById(R.id.okButton);
		cancelButton = (Button)findViewById(R.id.cancelButton);
		
		okButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				item.setText(taskEdit.getText().toString());
				item.setDueDate(new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()));
				Intent resultIntent = new Intent();
				resultIntent.putExtra("Item", item);
				resultIntent.putExtra("Position", position);
				setResult(RESULT_OK, resultIntent);
				finish();
			}
		});
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
	
}
