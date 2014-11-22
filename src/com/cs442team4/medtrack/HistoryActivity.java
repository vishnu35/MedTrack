package com.cs442team4.medtrack;

import com.cs442team4.medtrack.db.HisList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends Activity {

	HisList HL;
	ListView list;
	ArrayAdapter<String[]> listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		list = (ListView) findViewById(R.id.HisListView);

		// Creating the list adapter and populating the list
		listAdapter = new CustomListAdapter(this, R.layout.medlist);

		HL = new HisList(this);
		HL.openReadable();
		Cursor cursor = HL.getHisList();

		// if (cursor.getCount() == 0) {
		// TextView tv = (TextView) findViewById(R.id.MedListMsg);
		// tv.setVisibility(View.VISIBLE);
		// }

		while (cursor.moveToNext()) {
			String[] medcontent = {
					cursor.getString(cursor.getColumnIndex(HisList.MED_NAME)),
					cursor.getString(cursor
							.getColumnIndex(HisList.SCHEDULED_DATE_TIME)),
					cursor.getString(cursor
							.getColumnIndex(HisList.TAKEN_DATE_TIME)),
					String.valueOf(cursor.getInt(cursor
							.getColumnIndex(HisList.TAKEN))) };
			listAdapter.add(medcontent);
		}

		list.setAdapter(listAdapter);
	}

	@SuppressLint("InflateParams")
	class CustomListAdapter extends ArrayAdapter<String[]> {

		public CustomListAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.hislist,
						null);
			}
			((TextView) convertView.findViewById(R.id.HisLMedName))
					.setText(getItem(position)[0]);
			((TextView) convertView.findViewById(R.id.HisLMedScheduled))
					.setText("Scheduled: "+getItem(position)[1]);
			
			if (getItem(position)[3].equals("1")){
				((TextView) convertView.findViewById(R.id.HisLMedTaken))
				.setText("Taken : " + getItem(position)[2]);
				((ImageView) convertView.findViewById(R.id.HisLMedImage))
						.setImageResource(R.drawable.correct);
			}
			else{
				((TextView) convertView.findViewById(R.id.HisLMedTaken))
				.setText("Not Taken!");
				((ImageView) convertView.findViewById(R.id.HisLMedImage))
						.setImageResource(R.drawable.wrong);
			}
			return convertView;
		}

	}
}
