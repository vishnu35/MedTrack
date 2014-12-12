package com.cs442team4.medtrack.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.cs442team4.medtrack.MedicineActivity;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.obj.Medicine;

@SuppressLint({ "NewApi", "SimpleDateFormat", "ShowToast" })
public class SetReminders {
	static MedList ML;
	static Medicine medicine;
	static Context context;
	static long oneDayinMilli = 86400000;
	public static void Set(Context mContext, Medicine m) {
		context = mContext;
		medicine = m;
		for(int i=1;i<=4;i++){
			String sTime = getTimeString(i);
        	
			int tid = Integer.parseInt(String.valueOf(medicine.MED_ID)+"000"+i);
			Intent intent = new Intent(context, ReminderReceiver.class);
			intent.putExtra("id", (long)tid);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, tid, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
			if (sTime != null && !sTime.isEmpty()){
	        	try{
	        	String string1 = medicine.STARTDATE + " " + sTime + ":00";
	            Date sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(string1);
	            Calendar calendarTime = Calendar.getInstance();
	            calendarTime.setTime(sdf);

	            Calendar calendar2 = Calendar.getInstance();
	            Date now = calendar2.getTime();
	            
	            if (now.before(calendarTime.getTime())) {					
					am.setRepeating(AlarmManager.RTC_WAKEUP, calendarTime.getTimeInMillis(),oneDayinMilli, pendingIntent);	
	            }else{
	            	String[] tim = sTime.split(":");
	            	Calendar cal = Calendar.getInstance();
	            	cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(tim[0]));
	            	cal.set(Calendar.MINUTE,Integer.parseInt(tim[1]));
	            	cal.set(Calendar.SECOND,0);
	            	cal.set(Calendar.MILLISECOND,0);
	            	am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()+oneDayinMilli,oneDayinMilli, pendingIntent);
	            }
	        	}catch(Exception ex){
	        		
	        	}		
			}else{
				pendingIntent.cancel();
				am.cancel(pendingIntent);
			}			
		}		
	}
	
	public static void Remove(MedicineActivity mContext, Medicine m) {
		medicine = m;
		for(int i=1;i<=4;i++){
			int tid = Integer.parseInt(String.valueOf(medicine.MED_ID)+"000"+i);
			Intent intent = new Intent(mContext, ReminderReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, tid, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) mContext.getSystemService(Service.ALARM_SERVICE);
			pendingIntent.cancel();
			am.cancel(pendingIntent);						
		}
	}
	
	public static String getTimeString(int i){
		switch (i) {
		case 1:	return medicine.TIME1;			
		case 2:	return medicine.TIME2;			
		case 3:	return medicine.TIME3;			
		case 4: return medicine.TIME4;
		default:return "";
		}		
	}
}
