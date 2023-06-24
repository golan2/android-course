package com.selagroup.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ShoppingContentProvider extends ContentProvider {

	private static final String AUTHORITY = "com.selagroup.shopping";
	private static final String DATA_PATH = "shoppinglist";
	private static final String SCHEME = "content://";
	private static final String CONTENT_URI_STRING = SCHEME + AUTHORITY + "/" + DATA_PATH;
	
	public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
	public static final String COLLECTION_MIME_TYPE = "vnd.selagroup.cursor.dir/shoppinglist";
	public static final String ITEM_MIME_TYPE = "vnd.selagroup.cursor.item/shoppinglist";
	
	public static final String ID_PROPERTY = "_ID";
	public static final String ITEM_PROPERTY = "ITEM";
	public static final String AMOUNT_PROPERTY = "AMOUNT";
	public static final String[] ALL_PROPERTIES = { ID_PROPERTY, ITEM_PROPERTY, AMOUNT_PROPERTY };
	
	private static final Map<String,String> projectionMap;
	
	private static final UriMatcher matcher;
	private static final int URI_COLLECTION = 1;
	private static final int URI_ITEM = 2; 
	
	static {
		projectionMap = new HashMap<String,String>();
		projectionMap.put(ID_PROPERTY, DatabaseHelper.ID_COLUMN);
		projectionMap.put(ITEM_PROPERTY, DatabaseHelper.ITEM_COLUMN);
		projectionMap.put(AMOUNT_PROPERTY, DatabaseHelper.AMOUNT_COLUMN);
		
		matcher = new UriMatcher(UriMatcher.NO_MATCH);
		matcher.addURI(AUTHORITY, DATA_PATH, URI_COLLECTION);
		matcher.addURI(AUTHORITY, DATA_PATH + "/#", URI_ITEM);
	}
	
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	private static boolean isCollectionUri(Uri uri) {
		return matcher.match(uri) == URI_COLLECTION;
	}
	private ContentValues translateContentValues(ContentValues values) {
		ContentValues realValues = new ContentValues();
		for (Entry<String,Object> entry : values.valueSet()) {
			//NOTE: As a side effect, we convert everything to strings, but that's OK
			//for the types of data we have. If we had something more complex, like
			//arrays, we would have to perform the conversion manually.
			realValues.put(projectionMap.get(entry.getKey()), entry.getValue().toString());
		}
		return realValues;
	}
	private String translateSelection(String selection) {
		if (selection == null)
			return selection;
		
		//NOTE: This is really naive, we're just doing a text replace.
		String result = selection;
		for (Entry<String,String> proj : projectionMap.entrySet()) {
			result = result.replace(proj.getKey(), proj.getValue());
		}
		return result;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		int rowsAffected;
		if (isCollectionUri(uri)) {
			rowsAffected = db.delete(DatabaseHelper.SHOPPING_LIST_TABLE,
					translateSelection(selection), selectionArgs);
		} else {
			String id = uri.getPathSegments().get(1);
			String realSelection = DatabaseHelper.ID_COLUMN + "=" + id;
			if (!TextUtils.isEmpty(selection))
				realSelection += " AND (" + translateSelection(selection) + ")";
			rowsAffected = db.delete(DatabaseHelper.SHOPPING_LIST_TABLE,
					realSelection, selectionArgs);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsAffected;
	}

	@Override
	public String getType(Uri uri) {
		if (isCollectionUri(uri))
			return COLLECTION_MIME_TYPE;
		return ITEM_MIME_TYPE;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		if (!isCollectionUri(uri))
			throw new IllegalArgumentException("Invalid URI: " + uri);
		
		if (!values.containsKey(ITEM_PROPERTY))
			throw new IllegalArgumentException("Missing required property " + ITEM_PROPERTY);
		if (!values.containsKey(AMOUNT_PROPERTY))
			throw new IllegalArgumentException("Missing required property " + AMOUNT_PROPERTY);
		
		long rowId = db.insert(DatabaseHelper.SHOPPING_LIST_TABLE, null, translateContentValues(values));
		if (rowId > 0) {
			Uri newItemUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(newItemUri, null);
			return newItemUri;
		}
		
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		helper = new DatabaseHelper(getContext());
		db = helper.getWritableDatabase();
		return db != null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(DatabaseHelper.SHOPPING_LIST_TABLE);
		if (isCollectionUri(uri)) {
			qb.setProjectionMap(projectionMap);
		} else {
			qb.appendWhere(DatabaseHelper.ID_COLUMN + "=" + uri.getPathSegments().get(1));
		}
		Cursor cursor = qb.query(
				db, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		int rowsAffected;
		if (isCollectionUri(uri)) {
			rowsAffected = db.update(DatabaseHelper.SHOPPING_LIST_TABLE,
					translateContentValues(values), translateSelection(selection), selectionArgs);
		} else {
			String id = uri.getPathSegments().get(1);
			String realSelection = DatabaseHelper.ID_COLUMN + "=" + id;
			if (!TextUtils.isEmpty(selection))
				realSelection += " AND (" + translateSelection(selection) + ")";
			rowsAffected = db.update(DatabaseHelper.SHOPPING_LIST_TABLE,
					translateContentValues(values), realSelection, selectionArgs);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsAffected;
	}
}
