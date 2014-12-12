package com.cs442team4.medtrack;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TrackerActivity extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracker);
		
		TabHost th = getTabHost();
		Intent intent = new Intent(this , MedicineActivity.class);
		
		TabSpec ts = th.newTabSpec("SCHEDULES");
		ts.setContent(intent);
		ts.setIndicator("", getResources().getDrawable(R.drawable.schedule));
		th.addTab(ts);

		intent = new Intent(this , HistoryActivity.class);
		
		ts = th.newTabSpec("HISTORY");
		ts.setContent(intent);
		ts.setIndicator("", getResources().getDrawable(R.drawable.history));
		th.addTab(ts);	
		
		intent = new Intent(this , PrescriptionActivity.class);
		
		ts = th.newTabSpec("PRESCRIPTION");
		ts.setContent(intent);
		ts.setIndicator("", getResources().getDrawable(R.drawable.prescription));
		th.addTab(ts);
	}
}
