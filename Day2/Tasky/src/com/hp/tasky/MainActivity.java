package com.hp.tasky;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends Activity {

	private ArrayList<Task> tasks;
	private ArrayAdapter<Task> tasksAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initTasks();
        initList();
        setupAddListener();
        
        /* 
         * *** TODO ***
         * 1) Add options menu with three items:
         * 		Delete All, Preferences, Share
         * 2) Implement the "Delete All" option
         * 3) Add context menu for the ListView with two items:
         * 		Delete, Edit
         * 4) Implement the "Delete" option
         * *** BONUS ***
         * 5) Add another context menu item titled "Complete";
         *    when clicked it should mark the Task object as 
         *    completed, and its text should change to "[V] original title".
         *    (You will need to change Task.toString() for this.)
         */
    }

	private void initTasks() {
		tasks = new ArrayList<Task>();
		tasks.add(new Task(getString(R.string.sample_task_1)));
		tasks.add(new Task(getString(R.string.sample_task_2)));
	}

	private void setupAddListener() {
		findViewById(R.id.buttonAdd).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText editTaskTitle = (EditText) findViewById(R.id.editTaskTitle);
				tasksAdapter.add(new Task(editTaskTitle.getText().toString()));
			}
		});
	}

	private void initList() {
		ListView listTasks = (ListView) findViewById(R.id.listTasks);
		tasksAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, tasks);
		listTasks.setAdapter(tasksAdapter);
	}

}
