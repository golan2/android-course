package com.selagroup.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoListDatabaseHelper extends SQLiteOpenHelper {
	
	public ToDoListDatabaseHelper(Context context, String dbName, int dbVersion) {
		super(context, dbName, null, dbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table todolist (_id integer primary key autoincrement, task text not null, created long, due long, extra text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table todolist;");
		onCreate(db);
	}

}
