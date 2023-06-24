package com.hp.tasky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskyDBOpenHelper extends SQLiteOpenHelper {

	public TaskyDBOpenHelper(Context context) {
		super(context, "tasks_db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Call db.execSQL to create the database (single table)
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
