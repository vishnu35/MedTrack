package com.cs442team4.medtrack.helper;



import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.ReminderViewActivity;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.obj.Medicine;

@SuppressLint({ "InlinedApi", "NewApi" })
public class ReminderReceiver extends BroadcastReceiver {
	static MedList ML;
	static Medicine md;
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
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		boolean boolSound = preferences.getBoolean("checkboxSound", true);
		boolean boolVib = preferences.getBoolean("checkboxVib", true);
		boolean boolLight = preferences.getBoolean("checkboxLight", true);
		boolean boolWindow = preferences.getBoolean("window", true);
		String strSound = preferences.getString("soundPref", "Default");
		String strVib = preferences.getString("PrefVib", "Default");
		String strLight = preferences.getString("PrefLight", "Default");
		
		
		@SuppressWarnings("static-access")
		NotificationManager localNotificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);

		Intent localIntent = new Intent(context, ReminderViewActivity.class);
		localIntent.putExtra("id", reqId);
		localIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent localPendingIntent = PendingIntent.getActivity(context,
				(int) reqId, localIntent, Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		
		NotificationCompat.Builder localNotification = new NotificationCompat.Builder(context)
		.setContentTitle("Take Medicine : " + md.NAME)
        .setContentText(getTimeString(time))
        .setSmallIcon(R.drawable.ic_launcher)
        .setAutoCancel(false)
        .setContentIntent(localPendingIntent)
        .setWhen(System.currentTimeMillis());
		
		if(boolSound){
			if(strSound.equals("Default")){
				localNotification.setDefaults(Notification.DEFAULT_SOUND);
			}else{
				localNotification.setSound(Uri.parse(strSound));
			}
		}
		
		if(boolLight){
			switch (strLight) {
			case "Default":
				localNotification.setDefaults(Notification.DEFAULT_LIGHTS);
				break;
			case "Blue":
				localNotification.setLights(Color.BLUE, 100, 1000);
				break;
			case "Green":
				localNotification.setLights(Color.GREEN, 100, 1000);
				break;
			case "Red":
				localNotification.setLights(Color.RED, 100, 1000);
				break;
			case "White":
				localNotification.setLights(Color.WHITE, 100, 1000);
				break;
			default:
				break;
			}
		}
		
		if (boolVib) {
			if (strVib.equals("Default")) {
				localNotification.setDefaults(Notification.DEFAULT_VIBRATE);
			} else if (strVib.equals("Short")) {
				long[] pattern = new long[4];
				pattern[1] = 100L;
				pattern[2] = 500L;
				pattern[3] = 100L;
				localNotification.setVibrate(pattern);
			} else if (strVib.equals("Long")) {
				long[] pattern = new long[4];
				pattern[1] = 300L;
				pattern[2] = 1000L;
				pattern[3] = 300L;
				localNotification.setVibrate(pattern);
			}
		}
		
		localNotificationManager.notify((int) reqId, localNotification.build());
		
		if(boolWindow)
		context.startActivity(localIntent);
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
