package com.cs442team4.medtrack.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.cs442team4.medtrack.CreateMedActivity;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.obj.Medicine;

public class SetReminders {
	static MedList ML;
	static Medicine medicine;
	static Context context;
	public static void Set(CreateMedActivity mContext, Medicine m) {
		medicine = m;
		Intent intent = new Intent(mContext, ReminderReceiver.class);
		intent.putExtra("id", (long)medicine.MED_ID);
		intent.putExtra("time", m.TIME1);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, medicine.MED_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager) mContext.getSystemService(Service.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 15000 ,15000, pendingIntent);		
	}

}
