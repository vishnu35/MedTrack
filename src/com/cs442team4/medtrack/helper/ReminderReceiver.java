package com.cs442team4.medtrack.helper;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.ReminderViewActivity;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.obj.Medicine;

@SuppressLint("InlinedApi")
public class ReminderReceiver extends BroadcastReceiver {
	static MedList ML;
	static Medicine md;
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle localBundle = intent.getExtras();
		long reqId = localBundle.getLong("id", 0);
		long id = reqId/10000;
		long time = (int) (reqId%10);
		
		ML = new MedList(context);
		ML.openReadable();
		md = ML.getMedDetailsObj(id);
		ML.close();

		@SuppressWarnings("static-access")
		NotificationManager localNotificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		Notification localNotification = new Notification(
				R.drawable.ic_launcher, "Medicine : " + md.NAME,
				System.currentTimeMillis());
		Intent localIntent = new Intent(context, ReminderViewActivity.class);
		localIntent.putExtra("id", reqId);
		localIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent localPendingIntent = PendingIntent.getActivity(context,
				(int) reqId, localIntent, Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		localNotification.setLatestEventInfo(context, "Take Medicine!!",
				md.NAME +" : "+ getTimeString(time), localPendingIntent);
		localNotificationManager.notify((int) reqId, localNotification);
		context.startActivity(localIntent);
		
		//DailogMedicineDetails.app_launched(context, reqId);
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
}
