package com.cs442team4.medtrack;

import com.cs442team4.medtrack.helper.DailogChangePassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		//setContentView(R.xml.preferences);
		//populateSpinners();
		Preference button = (Preference)getPreferenceManager().findPreference("changepassword");      
	    if (button != null) {
	        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
	            @Override
	            public boolean onPreferenceClick(Preference arg0) {
	            	DailogChangePassword.app_launched(SettingsActivity.this);   
	                return true;
	            }
	        });     
	    }
	}
}