package com.selagroup.dataconsumer;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ShoppingListConsumerActivity extends Activity {
   	
	private Uri lastInserted;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView itemsList = (ListView)findViewById(R.id.itemsListView);
        String[] from = { "item", "amount" };
        int[] to = { android.R.id.text1, android.R.id.text2 };
        Cursor cursor = managedQuery(
        		Uri.parse("content://com.selagroup.shopping/shoppinglist"),
        		new String[] { "_ID", "ITEM", "AMOUNT" }, null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor, from, to);
        itemsList.setAdapter(adapter);
        
        Button insertButton = (Button)findViewById(R.id.insertItemButton);
        final Button deleteButton = (Button)findViewById(R.id.deleteItemButton);
        
        insertButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentValues cv = new ContentValues();
				cv.put("ITEM", "Sugar");
				cv.put("AMOUNT", 15);
				lastInserted = getContentResolver().insert(
						Uri.parse("content://com.selagroup.shopping/shoppinglist"), cv);
				Toast.makeText(ShoppingListConsumerActivity.this, "Inserted item: " + lastInserted, Toast.LENGTH_LONG).show();
				deleteButton.setEnabled(true);
			}
		});
        
        deleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getContentResolver().delete(lastInserted, null, null);
				lastInserted = null;
				deleteButton.setEnabled(false);
			}
		});
    }
}