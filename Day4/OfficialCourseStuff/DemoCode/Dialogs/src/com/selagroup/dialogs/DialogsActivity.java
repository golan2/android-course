package com.selagroup.dialogs;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DialogsActivity extends Activity {
    
	private TextView textView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView)findViewById(R.id.mainText);
    }
    
    private void selection() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Select Treats");
    	final String[] treats = { "Twix", "Mars", "Snickers", "M&M", "Fruit" };
    	final boolean[] checkedItems = { false, false, false, false, true };
    	//NOTE: setMessage is incompatible with setMultiChoiceItems. Hence, no message.
    	builder.setMultiChoiceItems(treats, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (which == 4 && !isChecked) {
					Toast.makeText(DialogsActivity.this, "Fruit is very healthy.", Toast.LENGTH_LONG).show();
				}
			}
		});
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < treats.length; ++i) {
					if (checkedItems[i]) {
						sb.append(treats[i]).append("\n");
					}
				}
				textView.setText("Chosen treats:\n" + sb.toString());
			}
    	});
    	builder.create().show();
    }
    private void prompt() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Prompt");
		builder.setMessage("Give me a string, please.");
		final EditText input = new EditText(this);
		input.setHint("string");
		builder.setView(input);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				textView.setText("User says: " + input.getText().toString() + ".");
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				textView.setText("User cancelled the dialog.");
			}
		});
		builder.create().show();
    }
    private void dos() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Error!");
    	builder.setMessage("A general protection fault occurred. What would you like to do?");
    	builder.setPositiveButton("Abort", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				textView.setText("Aborting...");
			}
		});
    	builder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				textView.setText("Retrying...");
			}
		});
    	builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				textView.setText("Ignoring...");
			}
		});
    	builder.create().show();
    	
    }
    private void activity() {
    	startActivity(new Intent(this, MyDialogActivity.class));
    }
    private void date() {
    	Calendar calendar = Calendar.getInstance();
    	DatePickerDialog datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				textView.setText("Date selected: " + new Date(year, monthOfYear, dayOfMonth).toGMTString());
			}
    	}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    	datePickerDialog.show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.selection:
    		selection();
    		return true;
    	case R.id.prompt:
    		prompt();
    		return true;
    	case R.id.dos:
    		dos();
    		return true;
    	case R.id.activity:
    		activity();
    		return true;
    	case R.id.date:
    		date();
    		return true;
    	}
    	return false;
    }
}