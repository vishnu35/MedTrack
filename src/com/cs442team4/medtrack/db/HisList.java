package com.cs442team4.medtrack.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cs442team4.medtrack.obj.History;

public class HisList {
	public static String TABLE_NAME = "hislist";
	public static String HIS_ID = "_id";
	public static String MED_ID = "med_id";
	public static String MED_NAME = "med_name";
	public static String TAKEN = "Taken";
	public static String SCHEDULED_DATE_TIME = "Scheduled_Datetime";
	public static String TAKEN_DATE_TIME ="Taken_Datetime";

	public static String TEXT_TYPE = "TEXT";
	public static String INT_TYPE = "INTEGER";

	DbHelper dbhelper;
	SQLiteDatabase db;
	Context context;

	private static final String SQL_CREATE_HISLIST = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME
			+ " ('"
			+ HIS_ID
			+ "' INTEGER PRIMARY KEY AUTOINCREMENT, '"
			+ MED_ID
			+ "' "
			+ INT_TYPE
			+ ", '"
			+ MED_NAME
			+ "' "
			+ TEXT_TYPE
			+ ", '"
			+ TAKEN
			+ "'"
			+ INT_TYPE
			+ ", '"
			+ SCHEDULED_DATE_TIME
			+ "' "
			+ TEXT_TYPE
			+ ", '"
			+ TAKEN_DATE_TIME
			+ "'"
			+ TEXT_TYPE
			+ " )";

	public static String getSqlCreateHislist() {
		return SQL_CREATE_HISLIST;
	}

	private static final String SQL_GET_ALL_HIS_LIST = "SELECT * FROM "
			+ TABLE_NAME + " ORDER BY " + HIS_ID + " DESC";
	private static final String SQL_GET_HIS_DETAILS = "SELECT * FROM "
			+ TABLE_NAME + " WHERE " + MED_ID + "=?";

	public HisList(Context context) {
		this.context = context;
		dbhelper = new DbHelper(context);
	}

	public HisList openWritable() {
		db = dbhelper.getWritableDatabase();
		return this;
	}

	public HisList openReadable() {
		db = dbhelper.getReadableDatabase();
		return this;
	}

	public void close() {
		dbhelper.close();
	}

	public long insertData(History H) {
		ContentValues conval = new ContentValues();
		conval.put(MED_ID, H.MED_ID);
		conval.put(MED_NAME, H.MED_NAME);
		conval.put(TAKEN, H.TAKEN);
		conval.put(SCHEDULED_DATE_TIME, H.SCHEDULED_DATE_TIME);
		conval.put(TAKEN_DATE_TIME, H.TAKEN_DATE_TIME);
		return db.insertOrThrow(TABLE_NAME, null, conval);
	}

	public Cursor getHisList() {
		return db.rawQuery(SQL_GET_ALL_HIS_LIST, null);
	}

	public Cursor getHisDetailsCursor(long id) {
		return db.rawQuery(SQL_GET_HIS_DETAILS,
				new String[] { String.valueOf(id) });
	}
	
	public History getHisDetailsObj(long id){
		History h = new History();
		Cursor cursor = db.rawQuery(SQL_GET_HIS_DETAILS,
				new String[] { String.valueOf(id) });		
		while (cursor.moveToNext()) {
			h.HIS_ID = (int) id;
			h.MED_ID=cursor.getInt(cursor.getColumnIndex(HisList.MED_ID));
			h.MED_NAME=cursor.getString(cursor.getColumnIndex(HisList.MED_NAME));
			h.SCHEDULED_DATE_TIME=cursor.getString(cursor.getColumnIndex(HisList.SCHEDULED_DATE_TIME));
			h.TAKEN_DATE_TIME=cursor.getString(cursor.getColumnIndex(HisList.TAKEN_DATE_TIME));				
		}
		cursor.close();
		return h;
	}

	public boolean deleteHis(long id) {		
		return db.delete(TABLE_NAME, MED_ID + "=?",
				new String[] { String.valueOf(id) }) > 0;
	}
	
	public boolean editHis(History H){
		ContentValues UpdateValues = new ContentValues();
		UpdateValues.put(MED_ID, H.MED_ID);
		UpdateValues.put(MED_NAME, H.MED_NAME);
		UpdateValues.put(TAKEN, H.TAKEN);
		UpdateValues.put(SCHEDULED_DATE_TIME, H.SCHEDULED_DATE_TIME);
		UpdateValues.put(TAKEN_DATE_TIME, H.TAKEN_DATE_TIME);	
		String where = HisList.HIS_ID+"="+H.HIS_ID;
        return db.update(HisList.TABLE_NAME, UpdateValues, where, null) > 0;
	}	
}
