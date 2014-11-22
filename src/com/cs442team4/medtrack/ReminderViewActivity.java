package com.cs442team4.medtrack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cs442team4.medtrack.db.HisList;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.helper.DailogMedicineDetails;
import com.cs442team4.medtrack.obj.History;
import com.cs442team4.medtrack.obj.Medicine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class ReminderViewActivity extends Activity {
	TextView MedVName, MedVDesc, MedVTiming;
	static MedList ML;
	static HisList HL;
	static Medicine md;
	static History hs;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder_view);

		Bundle localBundle = getIntent().getExtras();
		long id = localBundle.getLong("id", 0);

		MedVName = (TextView) findViewById(R.id.MedVName);
		MedVDesc = (TextView) findViewById(R.id.MedVDesc);
		MedVTiming = (TextView) findViewById(R.id.MedVTiming);

		ML = new MedList(this);
		ML.openReadable();
		md = ML.getMedDetailsObj(id);
		ML.close();
		String time = localBundle.getString("time", md.TIME1);

		MedVName.setText(md.NAME);
		MedVDesc.setText(md.DESCRIPTION);
		MedVTiming.setText(time);

	}

	public void BtnYesNoClick(View v) {
		hs = new History();
		hs.MED_ID = md.MED_ID;
		hs.MED_NAME = md.NAME;
		hs.TAKEN_DATE_TIME = getCurrentTime()+ ", " + getCurrentDate();
		hs.SCHEDULED_DATE_TIME = md.TIME1+ ", " + getCurrentDate();
		hs.TAKEN = 0;
		if (v.getId() == R.id.MedVBtnYes){
			hs.TAKEN = 1;
			ML = new MedList(this);
			ML.openWritable();
			ML.MedTaken(md.MED_ID, md.COUNT-1);
			ML.close();
		}
		HL = new HisList(this);
		HL.openWritable();
		long id = HL.insertData(hs);
		HL.close();
		finish();
	}
	
	public String getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");		
		Date date = new Date();
		return dateFormat.format(date).toString();
	}
	public String getCurrentTime(){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");		
		Date date = new Date();
		return dateFormat.format(date).toString();
	}
}
