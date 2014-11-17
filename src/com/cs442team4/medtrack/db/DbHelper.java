package com.cs442team4.medtrack.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "medtrack";
	public static final int DATABASE_VERSION = 1;

	public DbHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.v("qwer","asdf");
		try {
			db.execSQL(MedList.getSqlCreateMedlist());
			Log.v("qwer","created");
		} catch (SQLException e) {
			e.printStackTrace();
			Log.v("qwer","error occured");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + MedList.TABLE_NAME);
		onCreate(db);
	}
}
