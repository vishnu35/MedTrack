package com.cs442team4.medtrack.db;

import com.cs442team4.medtrack.obj.Medicine;

import android.annotation.SuppressLint;
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
	public static String TIME2 = "Time2";
	public static String TIME3 = "Time3";
	public static String TIME4 = "Time4";

	public static String TEXT_TYPE = "TEXT";
	public static String INT_TYPE = "INTEGER";

	DbHelper dbhelper;
	SQLiteDatabase db;
	Context context;

	private static final String SQL_CREATE_MEDLIST = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME
			+ " ('"
			+ MED_ID
			+ "' INTEGER PRIMARY KEY AUTOINCREMENT, '"
			+ NAME
			+ "' "
			+ TEXT_TYPE
			+ ", '"
			+ DESCRIPTION
			+ "'"
			+ TEXT_TYPE
			+ ", '"
			+ COUNT
			+ "' "
			+ INT_TYPE
			+ ", '"
			+ STARTDATE
			+ "'"
			+ TEXT_TYPE
			+ ", '"
			+ TIME1
			+ "' "
			+ TEXT_TYPE
			+ ", '"
			+ TIME2
			+ "' "
			+ TEXT_TYPE
			+ ", '"
			+ TIME3
			+ "' "
			+ TEXT_TYPE
			+ ", '"
			+ TIME4
			+ "' "
			+ TEXT_TYPE
			+ " )";

	public static String getSqlCreateMedlist() {
		return SQL_CREATE_MEDLIST;
	}

	private static final String SQL_GET_ALL_MED_LIST = "SELECT * FROM "
			+ TABLE_NAME;
	private static final String SQL_GET_MED_DETAILS = "SELECT * FROM "
			+ TABLE_NAME + " WHERE " + MED_ID + "=?";

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
		conval.put(TIME2, M.TIME2);
		conval.put(TIME3, M.TIME3);
		conval.put(TIME4, M.TIME4);
		return db.insertOrThrow(TABLE_NAME, null, conval);
	}

	public Cursor getMedList() {
		return db.rawQuery(SQL_GET_ALL_MED_LIST, null);
	}

	public Cursor getMedDetailsCursor(long id) {
		return db.rawQuery(SQL_GET_MED_DETAILS,
				new String[] { String.valueOf(id) });
	}
	
	public Medicine getMedDetailsObj(long id){
		Medicine md = new Medicine();
		Cursor cursor = db.rawQuery(SQL_GET_MED_DETAILS,
				new String[] { String.valueOf(id) });
		
		while (cursor.moveToNext()) {
			md.MED_ID = (int) id;
			md.NAME=cursor.getString(cursor.getColumnIndex(MedList.NAME));
			md.DESCRIPTION=cursor.getString(cursor.getColumnIndex(MedList.DESCRIPTION));
			md.COUNT=cursor.getInt(cursor.getColumnIndex(MedList.COUNT));
			md.STARTDATE=cursor.getString(cursor.getColumnIndex(MedList.STARTDATE));
			md.TIME1=cursor.getString(cursor.getColumnIndex(MedList.TIME1));
			md.TIME2=cursor.getString(cursor.getColumnIndex(MedList.TIME2));
			md.TIME3=cursor.getString(cursor.getColumnIndex(MedList.TIME3));
			md.TIME4=cursor.getString(cursor.getColumnIndex(MedList.TIME4));				
		}
		cursor.close();
		return md;
	}

	public boolean deleteMed(long id) {		
		return db.delete(TABLE_NAME, MED_ID + "=?",
				new String[] { String.valueOf(id) }) > 0;
	}
	
	public boolean editMed(Medicine md){
		ContentValues UpdateValues = new ContentValues();
		UpdateValues.put(MedList.NAME, md.NAME);
		UpdateValues.put(MedList.DESCRIPTION, md.DESCRIPTION);
		UpdateValues.put(MedList.COUNT, md.COUNT);
		UpdateValues.put(MedList.STARTDATE, md.STARTDATE);
		UpdateValues.put(MedList.TIME1, md.TIME1);
		UpdateValues.put(MedList.TIME2, md.TIME2);
		UpdateValues.put(MedList.TIME3, md.TIME3);
		UpdateValues.put(MedList.TIME4, md.TIME4);		
		String where = MedList.MED_ID+"="+md.MED_ID;
        return db.update(MedList.TABLE_NAME, UpdateValues, where, null) > 0;
	}
	
	@SuppressLint("NewApi")
	public String getTimings(Medicine md){
		String timings = "";
		if (!md.TIME1.isEmpty())
			timings = timings
					+ md.TIME1
					+ "  ";
		if (!md.TIME2.isEmpty())
			timings = timings
					+ md.TIME2
					+ "  ";
		if (!md.TIME3.isEmpty())
			timings = timings
					+ md.TIME3
					+ "  ";
		if (!md.TIME4.isEmpty())
			timings = timings
					+ md.TIME4
					+ "  ";		
		
		return timings;
		
	}
	
	@SuppressLint("NewApi")
	public static String getTimings(String t1, String t2, String t3, String t4){
		String timings = "";
		if (t1 != null && !t1.isEmpty())
			timings = timings
					+ t1
					+ "  ";
		if (t2 != null && !t2.isEmpty())
			timings = timings
					+ t2
					+ "  ";
		if (t3 != null && !t3.isEmpty())
			timings = timings
					+ t3
					+ "  ";
		if (t4 != null && !t4.isEmpty())
			timings = timings
					+ t4
					+ "  ";		
		
		return timings;
		
	}
}
