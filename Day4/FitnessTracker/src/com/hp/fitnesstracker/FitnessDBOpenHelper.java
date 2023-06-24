package com.hp.fitnesstracker;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FitnessDBOpenHelper extends SQLiteOpenHelper {

	public FitnessDBOpenHelper(Context context) {
		super(context, "fitness_db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL("create table activities ( _id integer primary key autoincrement, "
					  +" type text not null, activitydate integer not null )");
			// when = milliseconds Unix time
		} catch (SQLException ex) {
			Log.e("FitnessDBOpenHelper", "Error creating database: " + ex.toString());
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
