package com.cs442team4.medtrack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cs442team4.medtrack.db.HisList;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.obj.History;
import com.cs442team4.medtrack.obj.Medicine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class ReminderViewActivity extends Activity {
	TextView MedVName, MedVDesc, MedVTiming;
	static MedList ML;
	static HisList HL;
	static Medicine md;
	static History hs;
	static long extra, time;
	PowerManager.WakeLock mWakeLock;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Wake Log");
		mWakeLock.acquire();
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | 
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | 
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON , 
				WindowManager.LayoutParams.FLAG_FULLSCREEN |
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | 
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		setContentView(R.layout.activity_reminder_view);

		Bundle localBundle = getIntent().getExtras();
		extra = localBundle.getLong("id",0);
		long id = extra/10000;
		time = (int) (extra%10);

		MedVName = (TextView) findViewById(R.id.MedVName);
		MedVDesc = (TextView) findViewById(R.id.MedVDesc);
		MedVTiming = (TextView) findViewById(R.id.MedVTiming);

		ML = new MedList(this);
		ML.openReadable();
		md = ML.getMedDetailsObj(id);
		ML.close();
		

		MedVName.setText(md.NAME);
		MedVDesc.setText(md.DESCRIPTION);
		MedVTiming.setText(getTimeString(time));

	}

	public void BtnYesNoClick(View v) {
		hs = new History();
		hs.MED_ID = md.MED_ID;
		hs.MED_NAME = md.NAME;
		hs.TAKEN_DATE_TIME = getCurrentTime()+ ", " + getCurrentDate();
		hs.SCHEDULED_DATE_TIME = getTimeString(time)+ ", " + getCurrentDate();
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
		@SuppressWarnings("unused")
		long id = HL.insertData(hs);
		HL.close();
		
	    NotificationManager nMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    nMgr.cancel((int)extra);
		
		finish();
	}
	
	public static String getTimeString(long i){
		switch ((int)i) {
		case 1:	return md.TIME1;			
		case 2:	return md.TIME2;			
		case 3:	return md.TIME3;			
		case 4: return md.TIME4;
		default:return "";
		}		
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
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mWakeLock.release();
	}
}
