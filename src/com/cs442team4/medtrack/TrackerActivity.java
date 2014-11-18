package com.cs442team4.medtrack;

import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.helper.DailogMedicineDetails;

import android.annotation.SuppressLint;
import android.app.Activity;
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

public class TrackerActivity extends Activity {
	MedList ML;
	ListView list;
	ArrayAdapter<String[]> listAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracker);
		TabHost th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		TabSpec ts = th.newTabSpec("tag1");
		ts.setContent(R.id.tab1);
		ts.setIndicator("SCHEDULES");
		th.addTab(ts);

		ts = th.newTabSpec("tag2");
		ts.setContent(R.id.tab2);
		ts.setIndicator("HISTORY");
		th.addTab(ts);

		ts = th.newTabSpec("tag3");
		ts.setContent(R.id.tab3);
		ts.setIndicator("PRESCRIPTION");
		th.addTab(ts);

		
		list = (ListView)findViewById(R.id.MedListView);

        // Creating the list adapter and populating the list
		listAdapter = new CustomListAdapter(this, R.layout.medlist);
        
		ML = new MedList(this.getBaseContext());
		ML.openReadable();
		Cursor cursor = ML.getMedList();
		
		if(cursor.getCount()==0){
			TextView tv = (TextView)findViewById(R.id.MedListMsg);
			tv.setVisibility(View.VISIBLE);
		}
		
		while(cursor.moveToNext())
		{
			String timings = "";
			if(cursor.getInt(cursor.getColumnIndex(MedList.TIME1CHECK))== 1)
				timings = timings + cursor.getString(cursor.getColumnIndex(MedList.TIME1)) + "  ";
			if(cursor.getInt(cursor.getColumnIndex(MedList.TIME2CHECK))== 1)
				timings = timings + cursor.getString(cursor.getColumnIndex(MedList.TIME2)) + "  ";
			if(cursor.getInt(cursor.getColumnIndex(MedList.TIME3CHECK))== 1)
				timings = timings + cursor.getString(cursor.getColumnIndex(MedList.TIME3)) + "  ";
			if(cursor.getInt(cursor.getColumnIndex(MedList.TIME4CHECK))== 1)
				timings = timings + cursor.getString(cursor.getColumnIndex(MedList.TIME4)) + "  ";
			
			String[] medcontent = {cursor.getString(cursor.getColumnIndex(MedList.NAME)),timings,String.valueOf(cursor.getInt(cursor.getColumnIndex(MedList.COUNT))),String.valueOf(cursor.getInt(cursor.getColumnIndex(MedList.MED_ID)))};
			listAdapter.add(medcontent);
		}
            
        list.setAdapter(listAdapter);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				TextView medIdTv = (TextView)view.findViewById(R.id.MedListId);
				long MedId = 0;
				try{
					MedId = Integer.parseInt(medIdTv.getText().toString());					
				} catch (Exception ex) {
				}
				DailogMedicineDetails.app_launched(TrackerActivity.this, MedId);
			}
		});
        ML.close();
    }

    @SuppressLint("InflateParams")
	class CustomListAdapter extends ArrayAdapter<String[]> {

        public CustomListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.medlist, null);
            }            
            ((TextView)convertView.findViewById(R.id.MedListName)).setText(getItem(position)[0]);
            ((TextView)convertView.findViewById(R.id.MedListTimings)).setText(getItem(position)[1]);
            ((TextView)convertView.findViewById(R.id.MedListCount)).setText("Remaining Medicine Count : "+getItem(position)[2]);
            ((TextView)convertView.findViewById(R.id.MedListId)).setText(getItem(position)[3]);
            
            return convertView;
        }
        
        private void OnItemCli() {
			// TODO Auto-generated method stub

		}
        
        
        
    }

	public void AddMed(View v) {
		Intent intent = new Intent(this, CreateMedActivity.class);
		startActivity(intent);
	}

	public void settings(View v) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void TakePic(View v) {
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {

			Bitmap bp = (Bitmap) data.getExtras().get("data");
			ImageView TimageView1 = (ImageView) findViewById(R.id.TimageView1);
			TimageView1.setVisibility(View.VISIBLE);
			TimageView1.setImageBitmap(bp);
		}
	}
}
