package com.selagroup.todo;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ToDoDAL {
	
	public static final String TODOLIST_TABLE_NAME = "todolist";
	public static final String ID_COLUMN = "_id";
	public static final String TASK_COLUMN = "task";
	public static final String CREATED_COLUMN = "created";
	public static final String DUE_COLUMN = "due";
	public static final String EXTRA_COLUMN = "extra"; //For storing data such as the phone number to call;
													   //an alternative would be for each item type to provide
													   //its own interface to a special database name.
	public static final String[] ALL_COLUMNS = { ID_COLUMN, TASK_COLUMN, CREATED_COLUMN, DUE_COLUMN, EXTRA_COLUMN };
	
	private static final String TODOLIST_DB_NAME = "todolist";
	private static final int TODOLIST_DB_VERSION = 1;
	
	private Context context;
	private ToDoListDatabaseHelper helper;
	private SQLiteDatabase db;
	
	public ToDoDAL(Context ctx) {
		context = ctx;
		helper = new ToDoListDatabaseHelper(context, TODOLIST_DB_NAME, TODOLIST_DB_VERSION);
	}
	
	public void open() {
		db = helper.getWritableDatabase();
	}
	
	public void close() {
		db.close();
	}
	
	public void insertTask(ToDoItem task) {
		ContentValues taskValues = toContentValues(task);
		long rowId = db.insert(TODOLIST_TABLE_NAME, null, taskValues);
		task.setRowId(rowId);
	}
	
	public boolean updateTask(ToDoItem task) {
		ContentValues taskValues = toContentValues(task);
		return 0 < db.update(TODOLIST_TABLE_NAME, taskValues, ID_COLUMN + "=" + task.getRowId(), null);
	}
	
	public boolean removeTask(long rowId) {
		return 0 < db.delete(TODOLIST_TABLE_NAME, ID_COLUMN + "=" + rowId, null);
	}
	
	public boolean removeTask(ToDoItem task) {
		return removeTask(task.getRowId());
	}
	
	public Cursor getAllToDoItemsCursor() {
		return db.query(TODOLIST_TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
	}
	
	public ToDoItem getToDoItem(long rowId) {
		Cursor cursor = db.query(/*distinct*/true, TODOLIST_TABLE_NAME, ALL_COLUMNS,
				ID_COLUMN + "=" + rowId, null, null, null, null, null);
		if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
			throw new RuntimeException("No to do item found for row " + rowId);
		}
		return fromCurrentCursorPosition(cursor);
	}
	
	public static ToDoItem fromCurrentCursorPosition(Cursor cursor) {
		long rowId = cursor.getLong(cursor.getColumnIndex(ID_COLUMN));
		String task = cursor.getString(cursor.getColumnIndex(TASK_COLUMN));
		long created = cursor.getLong(cursor.getColumnIndex(CREATED_COLUMN));
		long due = cursor.getLong(cursor.getColumnIndex(DUE_COLUMN));
		String extraData = cursor.getString(cursor.getColumnIndex(EXTRA_COLUMN));
		ToDoItem result;
		
		//For now, assume that if a todo item has an "extra", then it's a CallToDoItem.
		//A real-life approach with an eye towards extensibility would be to have
		//a discriminator column in the table that specifies the type of item to create,
		//and a factory that creates items based on that column.
		if (extraData != null) {
			result = new CallToDoItem(task, extraData, new Date(created));
		} else {
			result = new ToDoItem(task, new Date(created));
		}
		result.setDueDate(new Date(due));
		result.setRowId(rowId);
		return result;
	}

	private ContentValues toContentValues(ToDoItem task) {
		ContentValues taskValues = new ContentValues();
		taskValues.put(TASK_COLUMN, task.getText());
		taskValues.put(CREATED_COLUMN, task.getCreationDate().getTime());
		Date due = task.getDueDate();
		if (due != null) {
			taskValues.put(DUE_COLUMN, due.getTime());
		}
		String extra = task.getExtraData();
		if (extra != null) {
			taskValues.put(EXTRA_COLUMN, extra);
		}
		return taskValues;
	}
	
}
