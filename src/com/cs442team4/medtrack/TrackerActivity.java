package com.cs442team4.medtrack;

import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.helper.DailogMedicineDetails;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class TrackerActivity extends TabActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracker);
		
		TabHost th = getTabHost();
		Intent intent = new Intent(this , MedicineActivity.class);
		
		TabSpec ts = th.newTabSpec("SCHEDULES");
		ts.setContent(intent);
		ts.setIndicator("SCHEDULES");
		th.addTab(ts);

		intent = new Intent(this , HistoryActivity.class);
		
		ts = th.newTabSpec("HISTORY");
		ts.setContent(intent);
		ts.setIndicator("HISTORY");
		th.addTab(ts);	
		
		intent = new Intent(this , PrescriptionActivity.class);
		
		ts = th.newTabSpec("PRESCRIPTION");
		ts.setContent(intent);
		ts.setIndicator("PRESCRIPTION");
		th.addTab(ts);
	}
}
