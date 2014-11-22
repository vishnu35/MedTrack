package com.cs442team4.medtrack.helper;

import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.ReminderViewActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class ReminderReceiver extends BroadcastReceiver {

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle localBundle = intent.getExtras();
		long reqId = localBundle.getLong("id", 0);
		Toast.makeText(context, "Received : " + reqId, Toast.LENGTH_LONG)
				.show();

		NotificationManager localNotificationManager = (NotificationManager) context
				.getSystemService("notification");
		Notification localNotification = new Notification(
				R.drawable.ic_launcher, "Notification : " + reqId,
				System.currentTimeMillis());
		Intent localIntent = new Intent(context, ReminderViewActivity.class);
		localIntent.putExtra("id", reqId);
		localIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent localPendingIntent = PendingIntent.getActivity(context,
				(int) reqId, localIntent, Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		localNotification.setLatestEventInfo(context, "Nofication2",
				"Nofication2 : " + reqId, localPendingIntent);
		localNotificationManager.notify((int) reqId, localNotification);
		context.startActivity(localIntent);
		
		//DailogMedicineDetails.app_launched(context, reqId);
	}
}
