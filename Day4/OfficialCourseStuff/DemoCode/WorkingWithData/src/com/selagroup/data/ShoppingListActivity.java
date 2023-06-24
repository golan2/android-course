package com.selagroup.data;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShoppingListActivity extends Activity {
	
	private AutoCompleteTextView itemText;
	private ListView itemList;
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	private Cursor cursor;
	private SimpleCursorAdapter adapter;
	private List<String> autoCompleteItems;
	private ArrayAdapter<String> autoCompleteAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        itemText = (AutoCompleteTextView)findViewById(R.id.itemsTextView);
        itemList = (ListView)findViewById(R.id.itemsListView);
               
        helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();
        cursor = db.query(DatabaseHelper.SHOPPING_LIST_TABLE, DatabaseHelper.ALL_COLUMNS, null, null, null, null, null);
        
        String[] from = { DatabaseHelper.ITEM_COLUMN, DatabaseHelper.AMOUNT_COLUMN };
        int[] to = { R.id.row_item, R.id.row_amount };
        adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);
        itemList.setAdapter(adapter);
        
        autoCompleteItems = new ArrayList<String>();
        cursor.moveToFirst();
        do {
        	autoCompleteItems.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_COLUMN)));
        } while (cursor.moveToNext());
        autoCompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoCompleteItems);
        itemText.setAdapter(autoCompleteAdapter);
        itemText.setThreshold(1);
        cursor.moveToFirst();
        
        itemText.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.this);
					builder.setTitle("How Many Items?");
					builder.setSingleChoiceItems(new String[] { "1", "2", "3", "4" }, 0, null);
					builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String item = itemText.getText().toString();
							int amount = ((AlertDialog)dialog).getListView().getCheckedItemPosition() + 1;
							ContentValues cv = new ContentValues();
							cv.put(DatabaseHelper.ITEM_COLUMN, item);
							cv.put(DatabaseHelper.AMOUNT_COLUMN, amount);
							db.insert(DatabaseHelper.SHOPPING_LIST_TABLE, null, cv);
							cursor.requery();
							if (!autoCompleteItems.contains(item)) {
								autoCompleteItems.add(item);
								autoCompleteAdapter.notifyDataSetChanged();
							}
						}
					});
					builder.create().show();
					return true;
				}
				return false;
			}
        });
        
        itemList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				cursor.moveToPosition(position);
				String rowId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ID_COLUMN));
				db.delete(DatabaseHelper.SHOPPING_LIST_TABLE, DatabaseHelper.ID_COLUMN + "=?", new String[] {rowId});
				cursor.requery();
			}
        });
    }
    
}