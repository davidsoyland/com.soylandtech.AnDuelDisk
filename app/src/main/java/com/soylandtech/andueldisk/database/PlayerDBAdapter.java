package com.soylandtech.andueldisk.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PlayerDBAdapter {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_WINS = "wins";
	public static final String KEY_LOSSES = "losses";
	public static final String KEY_NOTES = "notes";
	public static final int SORT_ASCENDING = 1; //"asc";
	public static final int SORT_DECENDING = 2; //"desc";
	private static final String DB_TABLE = "Players";
	private Context context;
	private SQLiteDatabase database;
	private PlayersDBHelper dbhelper;
	
	public PlayerDBAdapter(Context context){
		this.context = context;
	}
	
	public PlayerDBAdapter open() throws SQLException {
		dbhelper = new PlayersDBHelper(context);
		database = dbhelper.getWritableDatabase();
		return this;
		
	}
	
	public void close() {
		dbhelper.close();
	}
	
	public long createPlayer(String name, String notes){
		ContentValues intialValues = createContentValues(name,notes,0,0);
		return database.insert(DB_TABLE, null, intialValues);
	}
	

	public boolean updatePlayer(long rowId, String name, String notes, int wins, int losses){
		ContentValues updateValues = createContentValues(name,notes,wins,losses);
		return database.update(DB_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}
	public boolean updatePlayerWin(long rowId, int wins){
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_WINS, wins);
		return database.update(DB_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public boolean updatePlayerLosses(long rowId, int losses){
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_LOSSES, losses);
		return database.update(DB_TABLE, updateValues, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public boolean deletePlayer(long rowId){
		return database.delete(DB_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor getAllPlayers(String sortByKey, int sortOrder){
		String orderBy = sortByKey + " " + "asc";
		
		if (sortOrder == SORT_DECENDING){
			orderBy = sortByKey + " " + "desc";
		}
		
		return database.query(DB_TABLE, new String[] { KEY_ROWID, KEY_NAME, KEY_WINS, KEY_LOSSES}, 
								null, null, null, null, orderBy);
	}
	
	public Cursor getPlayer(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DB_TABLE, new String[]{
				KEY_ROWID, KEY_NAME, KEY_NOTES, KEY_WINS, KEY_LOSSES}, 
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	
	private ContentValues createContentValues(String name, String notes, int wins, int losses) {
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_NOTES, notes);
		values.put(KEY_WINS, wins);
		values.put(KEY_LOSSES, losses);
		return values;
	}
	
}
