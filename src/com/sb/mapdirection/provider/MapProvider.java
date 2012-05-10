package com.sb.mapdirection.provider;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class MapProvider extends ContentProvider {

	private static final String DB_NAME = "venues.db";
	private static final int DB_VERSION = 1;

	private static final String TABLE_VENUE = "venues";

	/*
	 * This class used to create DB while first lunch and Recreate new DB if db
	 * version has been changed for the furture release
	 */
	private static class DBHelper extends SQLiteOpenHelper {
		Context mContext;

		public DBHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			mContext = context;
			Log.d("CDMS", "DBHelper");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createVenueTable(db);
			instertVenue(db);
		}

		private void instertVenue(SQLiteDatabase db) {
			try {
				InputStream fileout = mContext.getAssets().open("venues.txt");
				BufferedInputStream bis = new BufferedInputStream(fileout);
				DataInputStream dis = new DataInputStream(bis);

				// dis.available() returns 0 if the file does not have more
				// lines.
				while (dis.available() != 0) {
					String query = dis.readLine();
					System.out.println(query);
					db.execSQL(query);
					query = null;
				}

				fileout.close();
				bis.close();
				dis.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void createVenueTable(SQLiteDatabase db) {
			StringBuffer query = new StringBuffer("create table " + TABLE_VENUE
					+ "(");
			query.append(BaseColumns._ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT , ");
			query.append(DBConstants.COLUMN_POSTAL_CODE + " TEXT, ");
			query.append(DBConstants.COLUMN_LAT + " TEXT ,");
			query.append(DBConstants.COLUMN_LONG + " TEXT , ");
			query.append(DBConstants.COLUMN_ADDRESS + " TEXT , ");
			query.append(DBConstants.COLUMN_TYPE + " INTEGER );");
			db.execSQL(query.toString());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		int count = 0;
		String tableName = null;
		try {
			switch (uriMatcher.match(uri)) {
			case CONTENT_NOTIFICATION:
				tableName = TABLE_VENUE;
				break;
			}
			count = db.delete(tableName, selection, selectionArgs);
		} catch (Exception e) {
		}
		return count;
	}

	@Override
	public String getType(Uri uri) {

		return null;
	}

	@Override
	public synchronized Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		long row_id = -1;
		String tableName = null;
		try {
			switch (uriMatcher.match(uri)) {
			case CONTENT_NOTIFICATION:
				tableName = TABLE_VENUE;
				row_id = db.insert(tableName, null, values);
				break;
			}
		} catch (Exception e) {
		}
		if (row_id != -1) {
			Uri insertUri = ContentUris.withAppendedId(uri, row_id);
			return insertUri;
		}
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String tableName = null;
		switch (uriMatcher.match(uri)) {
		case CONTENT_NOTIFICATION:
			tableName = TABLE_VENUE;
			break;
		}
		cursor = db.query(tableName, projection, selection, selectionArgs,
				null, null, sortOrder);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		int count = 0;
		String tableName = null;
		try {
			switch (uriMatcher.match(uri)) {
			case CONTENT_NOTIFICATION:
				tableName = TABLE_VENUE;
				break;
			}
			count = db.update(tableName, values, selection, selectionArgs);
		} catch (Exception e) {
		}
		return count;
	}

	private static UriMatcher uriMatcher;
	private DBHelper dbhelper;

	public static final int CONTENT_NOTIFICATION = 10;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		uriMatcher.addURI(DBConstants.AUTHORITY, DBConstants.CONTENT_VENUE,
				CONTENT_NOTIFICATION);
	}

	@Override
	public boolean onCreate() {
		dbhelper = new DBHelper(getContext());
		return true;
	}

	public SQLiteDatabase getDatabase() {
		return dbhelper.getWritableDatabase();
	}

}
