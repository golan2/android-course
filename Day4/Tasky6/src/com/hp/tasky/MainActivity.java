package com.hp.tasky;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;


public class MainActivity extends Activity implements OnItemClickListener {

	private static final int EDIT_ACTIVITY_REQUEST_CODE = 42;
	private ArrayList<Task> tasks;
	private ArrayAdapter<Task> tasksAdapter;
	private int currentlyEditedTaskPosition = -1;
	private SQLiteDatabase db;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initTasks();
        initList();
        setupAddListener();
        connectToDB();
    }
    
    private void connectToDB() {
		db = new TaskyDBOpenHelper(this).getWritableDatabase();
		
		// TODO Read all the tasks from the database and add them to tasksAdapter
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
    		clearAfterConfirming();
    		break;
    	case R.id.menuItemPreferences:
    		break;
    	case R.id.menuItemShare:
    		share();
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    private void clearAfterConfirming() {
    	new AlertDialog.Builder(this)
    		.setTitle("Confirmation")
    		.setMessage("Are you sure you want to clear all the tasks?")
    		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					tasksAdapter.clear();
					// TODO Delete all tasks from the database
				}
			})
    		.setNegativeButton("No", null)
    		.create()
    		.show();
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
    		// TODO Delete that task from the database
    		break;
    	case R.id.contextMenuItemEdit:
    		Intent editIntent = new Intent(this, EditActivity.class);
    		editIntent.putExtra("title", task.getTitle());
    		currentlyEditedTaskPosition = info.position;
    		startActivityForResult(editIntent, EDIT_ACTIVITY_REQUEST_CODE);
    		break;
    	case R.id.contextMenuItemComplete:
    		task.setCompleted(true);
    		tasksAdapter.notifyDataSetChanged();
    		// TODO Update the task's completion status
    		break;
    	case R.id.contextMenuItemSnooze:
    		task.snooze24Hours();
    		tasksAdapter.notifyDataSetChanged();
    		// TODO Update the task's due date
    		break;
    	}
    	return super.onContextItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK) {
    		String newTitle = data.getStringExtra("title");
    		tasksAdapter.getItem(currentlyEditedTaskPosition).setTitle(newTitle);
    		tasksAdapter.notifyDataSetChanged();
    		// TODO Update the task in the database
    	}
    	currentlyEditedTaskPosition = -1;
    	super.onActivityResult(requestCode, resultCode, data);
    }

	private void initTasks() {
		tasks = new ArrayList<Task>();
	}

	private void setupAddListener() {
		findViewById(R.id.buttonAdd).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText editTaskTitle = (EditText) findViewById(R.id.editTaskTitle);
				tasksAdapter.add(new Task(editTaskTitle.getText().toString()));
				// TODO Insert the task to the database
			}
		});
	}

	private void initList() {
		ListView listTasks = (ListView) findViewById(R.id.listTasks);
		tasksAdapter = new TaskAdapter(this, tasks);
		listTasks.setAdapter(tasksAdapter);
		registerForContextMenu(listTasks);
		listTasks.setOnItemClickListener(this);
	}

    private void share() {
    	String allTasks = "Your tasks:\n";
    	for (Task task : tasks) {
    		allTasks += task.getTitle() + "\n";
    	}
    	Intent shareIntent = new Intent(Intent.ACTION_SEND);
    	shareIntent.setType("text/plain");
    	shareIntent.putExtra(Intent.EXTRA_TEXT, allTasks);
    	startActivity(shareIntent);
    }
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Task task = tasksAdapter.getItem(position);
		String taskTitle = task.getTitle();
		if (taskTitle.matches("(\\d|-){3,12}")) {
			Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + taskTitle));
			startActivity(dialIntent);
		}
	}

}
