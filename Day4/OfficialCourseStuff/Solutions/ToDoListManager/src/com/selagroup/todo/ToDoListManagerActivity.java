package com.selagroup.todo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ToDoListManagerActivity extends Activity {
	
	private static final int PICK_CONTACT_CODE = 1;
	private static final int EDIT_ITEM_CODE = 2;
	
	private EditText newItemText;
	private ListView itemsList;
	private List<ToDoItem> items;
	private ArrayAdapter<ToDoItem> adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        newItemText = (EditText)findViewById(R.id.todoItemEditText);
        itemsList = (ListView)findViewById(R.id.todoItemListView);
        
        items = new ArrayList<ToDoItem>();
        adapter = new ToDoItemAdapter(this, items);
        itemsList.setAdapter(adapter);
        
        newItemText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
					ToDoItem item = new ToDoItem(newItemText.getText().toString());
					items.add(item);
					adapter.notifyDataSetChanged();
					newItemText.setVisibility(View.GONE);
					return true;
				}
				return false;
			}
        });
        
        registerForContextMenu(itemsList);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.mainmenu, menu);
    	return true;
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
		items.remove(position);
		adapter.notifyDataSetChanged();
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
				items.add(item);
				adapter.notifyDataSetChanged();
				return;
			}
			String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
			cursor.close();
			cursor = managedQuery(Phone.CONTENT_URI, null, Phone.CONTACT_ID + "=?", new String[] { contactId }, null);
			cursor.moveToFirst();
			String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(Phone.NUMBER));
			cursor.close();
			CallToDoItem item = new CallToDoItem(displayName, phoneNumber);
			items.add(item);
			adapter.notifyDataSetChanged();
		} else if (requestCode == EDIT_ITEM_CODE && resultCode == RESULT_OK) {
			ToDoItem item = (ToDoItem) data.getSerializableExtra("Item");
			int position = data.getIntExtra("Position", -1);
			if (position != -1) {
				items.set(position, item);
				adapter.notifyDataSetChanged();
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
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int itemId = item.getItemId();
		if (itemId == R.id.ctx_delete_item) {
			items.remove(info.position);
			adapter.notifyDataSetChanged();
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