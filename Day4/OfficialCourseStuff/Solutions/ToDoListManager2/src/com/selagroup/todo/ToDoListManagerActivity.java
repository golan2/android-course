package com.selagroup.todo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoListManagerActivity extends Activity {
	
	private static final int PICK_CONTACT_CODE = 1;
	private static final int EDIT_ITEM_CODE = 2;
	
	private EditText newItemText;
	private ListView itemsList;
	private List<ToDoItem> items;
	private ToDoDAL dal;
	private Cursor cursor;
	private String defaultText = "";
	private boolean deleteEnabled;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        items = new ArrayList<ToDoItem>();
        
        newItemText = (EditText)findViewById(R.id.todoItemEditText);
        itemsList = (ListView)findViewById(R.id.todoItemListView);
        
        dal = new ToDoDAL(this);
        dal.open();
        cursor = dal.getAllToDoItemsCursor();
        startManagingCursor(cursor);
        ToDoItemAdapter adapter = new ToDoItemAdapter(this, cursor);
        itemsList.setAdapter(adapter);
        updateItemsArray();
        
        newItemText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
					ToDoItem item = new ToDoItem(newItemText.getText().toString());
					dal.insertTask(item);
					updateItemsArray();
					newItemText.setText(defaultText);
					newItemText.setVisibility(View.GONE);
					return true;
				}
				return false;
			}
        });
        
        registerForContextMenu(itemsList);
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        deleteEnabled = prefs.getBoolean("delete_enabled", true);
        defaultText = prefs.getString("default_text", "");
        newItemText.setText(defaultText);
        prefs.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
				if ("delete_enabled".equals(key)) {
					deleteEnabled = sharedPreferences.getBoolean(key, true);
				} else if ("default_text".equals(key)) {
					defaultText = sharedPreferences.getString(key, "");
					newItemText.setText(defaultText);
				}
			}
		});
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if (dal != null) {
    		dal.close();
    	}
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	if (savedInstanceState != null) {
    		if (savedInstanceState.containsKey("UnsavedText")) {
    			newItemText.setText(savedInstanceState.getString("UnsavedText"));
    		}
    		if (savedInstanceState.containsKey("EditVisible")) {
    			boolean visible = savedInstanceState.getBoolean("EditVisible");
    			newItemText.setVisibility(visible ? View.VISIBLE : View.GONE);
    		}
    	}
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	outState.putString("UnsavedText", newItemText.getText().toString());
    	outState.putBoolean("EditVisible", newItemText.getVisibility() == View.VISIBLE);
    }
    
	private void updateItemsArray() {
		items.clear();
		cursor.requery();
		if (cursor.moveToFirst()) {
			do {
				items.add(ToDoDAL.fromCurrentCursorPosition(cursor));
			} while (cursor.moveToNext());
		}
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.mainmenu, menu);
    	return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	MenuItem item = menu.findItem(R.id.delete_item);
    	item.setEnabled(deleteEnabled);
    	return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.add_new_item:
    		addNewItem();
    		return true;
    	case R.id.delete_item:
    		deleteItem();
    		return true;
    	case R.id.add_new_call:
    		addNewCall();
    		return true;
    	case R.id.preferences:
    		showPreferences();
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }

	private void addNewItem() {
		newItemText.setVisibility(View.VISIBLE);
		newItemText.requestFocus();
	}
	
	private void addNewCall() {
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(intent, PICK_CONTACT_CODE);
	}
	
	private void deleteItem() {
		int position = itemsList.getSelectedItemPosition();
		if (position == ListView.INVALID_POSITION) {
			return;
		}
		dal.removeTask(items.get(position));
		updateItemsArray();
	}
	
	private void showPreferences() {
		Intent showPrefs = new Intent(this, ToDoPreferencesActivity.class);
		startActivity(showPrefs);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_CONTACT_CODE && resultCode == RESULT_OK) {
			Cursor cursor = managedQuery(data.getData(), null, null, null, null);
			cursor.moveToFirst();
			String displayName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
			if ("0".equals(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)))) {
				ToDoItem item = new ToDoItem("Call " + displayName);
				dal.insertTask(item);
				updateItemsArray();
				return;
			}
			String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
			cursor.close();
			cursor = managedQuery(Phone.CONTENT_URI, null, Phone.CONTACT_ID + "=?", new String[] { contactId }, null);
			cursor.moveToFirst();
			String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(Phone.NUMBER));
			cursor.close();
			CallToDoItem item = new CallToDoItem(displayName, phoneNumber);
			dal.insertTask(item);
			updateItemsArray();
		} else if (requestCode == EDIT_ITEM_CODE && resultCode == RESULT_OK) {
			ToDoItem item = (ToDoItem) data.getSerializableExtra("Item");
			int position = data.getIntExtra("Position", -1);
			if (position != -1) {
				dal.updateTask(item);
				updateItemsArray();
			}
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {	
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		ToDoItem item = items.get(info.position);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listcontextmenu, menu);
		item.buildContextMenu(menu);
		MenuItem deleteItemMenuItem = menu.findItem(R.id.ctx_delete_item);
		deleteItemMenuItem.setEnabled(deleteEnabled);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int itemId = item.getItemId();
		if (itemId == R.id.ctx_delete_item) {
			dal.removeTask(items.get(info.position));
			updateItemsArray();
		} else if (itemId == R.id.ctx_edit_item) {
			Intent intent = new Intent(this, EditToDoItemActivity.class);
			intent.putExtra("Item", items.get(info.position));
			intent.putExtra("Position", info.position);
			startActivityForResult(intent, EDIT_ITEM_CODE);
		} else {
			ToDoItem todoItem = items.get(info.position);
			if (todoItem.contextMenuClicked(this, itemId)) return true;
		}
		return super.onContextItemSelected(item);
	}
    
}