package com.cs442team4.medtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		populateSpinners();
	}
	
	public void SetPref(View v){
		Intent intent = new Intent(this, TrackerActivity.class);
		startActivity(intent);
		this.finish();
    }	
	
	private void populateSpinners() { 
		Spinner updateFreqSpinner = (Spinner)findViewById(R.id.spinner_update_freq);
		Spinner magnitudeSpinner = (Spinner)findViewById(R.id.spinner_quake_mag);
	    // Populate the update frequency spinner
	    ArrayAdapter<CharSequence> fAdapter;
	    fAdapter = ArrayAdapter.createFromResource(this, R.array.TONE,
	                                             android.R.layout.simple_spinner_item);
	    int spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;
	    fAdapter.setDropDownViewResource(spinner_dd_item);
	    updateFreqSpinner.setAdapter(fAdapter);
	    // Populate the minimum magnitude spinner
	    ArrayAdapter<CharSequence> mAdapter;
	    mAdapter = ArrayAdapter.createFromResource(this,
	      R.array.TIME,
	      android.R.layout.simple_spinner_item);
	    mAdapter.setDropDownViewResource(spinner_dd_item);
	    magnitudeSpinner.setAdapter(mAdapter);
	  }
}
