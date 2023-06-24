package com.selagroup.usingproviders;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

@SuppressWarnings("deprecation") //Because of legacy Contacts API
public class UsingContentProvidersActivity extends ListActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Cursor cursor = managedQuery(Contacts.Phones.CONTENT_URI, null, null, null, null);
        String[] from = { Contacts.Phones.DISPLAY_NAME, Contacts.Phones.NUMBER };
        int[] to = { R.id.nameTextView, R.id.phoneTextView };
        
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);
        setListAdapter(adapter);
        
        Button insertPersonButton = (Button)findViewById(R.id.insertPersonButton);
        insertPersonButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put(Contacts.People.NAME, "Jane");
				Uri uri = getContentResolver().insert(Contacts.People.CONTENT_URI, values);
				Toast.makeText(UsingContentProvidersActivity.this, "Added new person: " + uri.toString(), Toast.LENGTH_LONG).show();
			}
		});
        
        Button insertMediaButton = (Button)findViewById(R.id.insertMediaButton);
        insertMediaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues(3);
				values.put(Media.DISPLAY_NAME, "android_default");
				values.put(Media.DESCRIPTION, "Default Android application icon");
				values.put(Media.MIME_TYPE, "image/jpeg");

				Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
			    OutputStream outStream;
				try {
					outStream = getContentResolver().openOutputStream(uri);
				    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
				    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
				    outStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}