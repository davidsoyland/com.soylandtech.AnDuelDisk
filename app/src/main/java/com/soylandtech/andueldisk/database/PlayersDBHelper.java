package com.soylandtech.andueldisk.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlayersDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "andueldisk";
	private static final int DB_VER = 2;
	
	private static final String DB_CREATE = "CREATE  TABLE Players "
	+ "(_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "
	+ "name TEXT NOT NULL , notes TEXT , wins INTERGER, losses INTEGER);";
	
	public PlayersDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DB_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldver, int newver) {
		Log.w(PlayersDBHelper.class.getName(), "Upgrading Database from version "
				+ oldver + " to " + newver + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS Players");
		onCreate(database);

	}

}
