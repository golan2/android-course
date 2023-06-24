package com.selagroup.fileeditor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FileEditorActivity extends Activity {
	
	private static final int PICK_FILE = 1;
	
	private EditText textArea;
	private Button saveButton;
	private Button openButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        saveButton = (Button)findViewById(R.id.saveButton);
        openButton = (Button)findViewById(R.id.openButton);
        textArea = (EditText)findViewById(R.id.mainText);
        
        Resources res = getResources();
        InputStream sampleData = res.openRawResource(R.raw.sample);
        try {
			readFromStream(sampleData);
	        sampleData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        openButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent pickerIntent = new Intent(FileEditorActivity.this, FilePickerActivity.class);
				startActivityForResult(pickerIntent, PICK_FILE);
			}
        });
        saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(FileEditorActivity.this);
				builder.setTitle("Save");
				builder.setMessage("Choose a file name");
				final EditText input = new EditText(FileEditorActivity.this);
				input.setHint("file name");
				builder.setView(input);
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String filename = input.getText().toString();
						String text = textArea.getText().toString();
						try {
							FileOutputStream stream = openFileOutput(filename, MODE_PRIVATE);
							DataOutputStream dataOutput = new DataOutputStream(stream);
							dataOutput.writeBytes(text);
							dataOutput.close();
							stream.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { }
				});
				builder.create().show();
			}
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == PICK_FILE && resultCode == RESULT_OK) {
    		String file = data.getStringExtra(FilePickerActivity.SELECTED_FILE);
    		try {
				FileInputStream stream = openFileInput(file);
				readFromStream(stream);
				stream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }

	private void readFromStream(InputStream stream) throws IOException {
		DataInputStream dataStream = new DataInputStream(stream);
		StringBuilder text = new StringBuilder("");
		String line;
		while ((line = dataStream.readLine()) != null) {
			text.append(line).append("\n");
		}
		textArea.setText(text);
		dataStream.close();
	}
}