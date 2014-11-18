package com.cs442team4.medtrack.db;


import com.cs442team4.medtrack.obj.Medicine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedList {	
	public static String TABLE_NAME = "medlist";
	public static String MED_ID = "_id";
	public static String NAME = "Name";
	public static String DESCRIPTION = "Description";
	public static String COUNT = "Count";
	public static String STARTDATE = "StartDate";
	public static String TIME1 = "Time1";
	public static String TIME1CHECK = "Time1Check";
	public static String TIME2 = "Time2";
	public static String TIME2CHECK = "Time2Check";
	public static String TIME3 = "Time3";
	public static String TIME3CHECK = "Time3Check";
	public static String TIME4 = "Time4";
	public static String TIME4CHECK = "Time4Check";
	public static String REPEAT = "Repeat";
	
	public static String TEXT_TYPE = "TEXT";
	public static String INT_TYPE = "INTEGER";
	
	DbHelper dbhelper;
	SQLiteDatabase db;
	Context context;
	
	private static final String SQL_CREATE_MEDLIST =
		    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ('" +
		    		MED_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
		    		NAME + "' " +TEXT_TYPE + ", '"+
		    		DESCRIPTION + "'" +TEXT_TYPE + ", '"+
		    		COUNT + "' " +INT_TYPE+", '"+
		    		STARTDATE + "'" +TEXT_TYPE + ", '"+
		    		TIME1 + "' " +TEXT_TYPE+", '"+
		    		TIME1CHECK + "'" +INT_TYPE+", '"+
		    		TIME2 + "' " +TEXT_TYPE + ", '"+
		    		TIME2CHECK + "'" +INT_TYPE+", '"+
		    		TIME3 + "' " +TEXT_TYPE + ", '"+
		    		TIME3CHECK + "'" +INT_TYPE+", '"+
		    		TIME4 + "' " +TEXT_TYPE + ", '"+
		    		TIME4CHECK + "' " +INT_TYPE+", '"+
		    		REPEAT + "' " +INT_TYPE + " )";

	public static String getSqlCreateMedlist() {
		return SQL_CREATE_MEDLIST;
	}
	
	private static final String SQL_GET_ALL_MED_LIST = "SELECT * FROM " + TABLE_NAME;
	private static final String SQL_GET_MED_DETAILS = "SELECT * FROM " + TABLE_NAME + " WHERE "+MED_ID+"=?";
	
	public MedList(Context context) {
		this.context = context;
		dbhelper = new DbHelper(context);
	}
	
	public MedList openWritable() {
		db = dbhelper.getWritableDatabase();
		return this;
	}
	
	public MedList openReadable() {
		db = dbhelper.getReadableDatabase();
		return this;
	}

	public void close() {
		dbhelper.close();
	}

	public long insertData(Medicine M) {
		ContentValues conval = new ContentValues();
		conval.put(NAME, M.NAME);
		conval.put(DESCRIPTION, M.DESCRIPTION);
		conval.put(COUNT, M.COUNT);
		conval.put(STARTDATE, M.STARTDATE);
		conval.put(TIME1, M.TIME1);
		conval.put(TIME1CHECK, M.TIME1CHECK);
		conval.put(TIME2, M.TIME2);
		conval.put(TIME2CHECK, M.TIME2CHECK);
		conval.put(TIME3, M.TIME3);
		conval.put(TIME3CHECK, M.TIME3CHECK);
		conval.put(TIME4, M.TIME4);
		conval.put(TIME4CHECK, M.TIME4CHECK);
		conval.put(REPEAT, M.REPEAT);
		return db.insertOrThrow(TABLE_NAME, null, conval);
	}
		
	public Cursor getMedList(){
		return db.rawQuery(SQL_GET_ALL_MED_LIST, null);
	}
	
	public Cursor getMedDetails(long id){
		return db.rawQuery(SQL_GET_MED_DETAILS,	new String[] { String.valueOf(id) });
	}
}
