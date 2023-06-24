package com.selagroup.calllog;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.SimpleCursorAdapter;

public class CallLogListActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Cursor cursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        String[] from = { CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER };
        int[] to = { R.id.nameTextView, R.id.phoneTextView };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_layout, cursor, from, to);
        setListAdapter(adapter);
    }
}