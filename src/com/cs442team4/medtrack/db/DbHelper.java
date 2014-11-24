package com.cs442team4.medtrack.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "medtrack";
	public static final int DATABASE_VERSION = 3;

	public DbHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(MedList.getSqlCreateMedlist());
			db.execSQL(HisList.getSqlCreateHislist());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + MedList.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + HisList.TABLE_NAME);
		onCreate(db);
	}
}
