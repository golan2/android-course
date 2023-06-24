package com.hp.tasky;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;


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
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.mainmenu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menuItemDeleteAll:
    		tasksAdapter.clear();
    		break;
    	case R.id.menuItemPreferences:
    		break;
    	case R.id.menuItemShare:
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	getMenuInflater().inflate(R.menu.contextmenu, menu);
    	super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	Task task = tasksAdapter.getItem(info.position);
    	switch (item.getItemId()) {
    	case R.id.contextMenuItemDelete:
    		tasksAdapter.remove(task);
    		break;
    	case R.id.contextMenuItemEdit:
    		break;
    	case R.id.contextMenuItemComplete:
    		task.setCompleted(true);
    		tasksAdapter.notifyDataSetChanged();
    		break;
    	}
    	return super.onContextItemSelected(item);
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
		registerForContextMenu(listTasks);
	}

}
