package com.hp.tasky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class EditActivity extends Activity implements OnClickListener {

	private EditText editTaskTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		Intent creatingIntent = getIntent();
		String title = creatingIntent.getStringExtra("title");
		editTaskTitle = (EditText) findViewById(R.id.editTaskTitle);
		editTaskTitle.setText(title);
		
		findViewById(R.id.buttonSave).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String newTitle = editTaskTitle.getText().toString();
		Intent resultIntent = new Intent();
		resultIntent.putExtra("title", newTitle);
		setResult(RESULT_OK, resultIntent);
		finish();
	}

}
