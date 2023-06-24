package com.selagroup.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String SHOPPING_LIST_TABLE = "shoppinglist";
	public static final String ID_COLUMN = "_id";
	public static final String ITEM_COLUMN = "item";
	public static final String AMOUNT_COLUMN = "amount";
	public static final String[] ALL_COLUMNS = { ID_COLUMN, ITEM_COLUMN, AMOUNT_COLUMN };
	
	private static final String DATABASE_NAME = "ShoppingList";
	private static final int DATABASE_VERSION = 2;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create the database tables. It's also reasonable to populate them with initial values.
		db.execSQL("create table shoppinglist (_id integer primary key autoincrement, item text, amount integer);");
		ContentValues cv = new ContentValues();
		cv.put(ITEM_COLUMN, "My Application");
		cv.put(AMOUNT_COLUMN, 1);
		db.insert(SHOPPING_LIST_TABLE, null, cv);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//NOTE: This approach for "upgrading" is rather crude -- would be a much better idea to migrate the data.
		db.execSQL("drop table shoppinglist;");
		onCreate(db);
	}

}
