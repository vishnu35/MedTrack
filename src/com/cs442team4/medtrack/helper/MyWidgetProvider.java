package com.cs442team4.medtrack.helper;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetProvider;

import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.db.MedList;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.RemoteViews;

@SuppressLint("NewApi")
public class MyWidgetProvider extends AppWidgetProvider {

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);

		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		for (int widgetId : allWidgetIds) {

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);

			MedList ML = new MedList(context);
			ML.openReadable();
			Cursor cursor = ML.getMedList();
			int i=1;
			while (cursor.moveToNext()) {
				int layId = context.getResources().getIdentifier("wgMedLay"+i, "id",context.getPackageName());
				int nameId = context.getResources().getIdentifier("wgMedListName"+i, "id",context.getPackageName());
				int countId = context.getResources().getIdentifier("wgMedListCount"+i, "id",context.getPackageName());
				int timeId = context.getResources().getIdentifier("wgMedListTimings"+i, "id",context.getPackageName());
				remoteViews.setViewVisibility(layId, View.VISIBLE);
				remoteViews.setTextViewText(nameId, cursor.getString(cursor.getColumnIndex(MedList.NAME)));
				remoteViews.setTextViewText(countId, "("+String.valueOf(cursor.getInt(cursor.getColumnIndex(MedList.COUNT)))+")");
				remoteViews.setTextViewText(timeId, cursor.getString(cursor.getColumnIndex(MedList.TIME1)));
				i++;
			}

			
			Intent intent = new Intent(context, MyWidgetProvider.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.widget_layout,
					pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}

	@Override
	public void onEnabled(Context context) {

		super.onEnabled(context);
	}
}