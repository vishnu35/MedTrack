package com.cs442team4.medtrack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cs442team4.medtrack.R;

public class MainActivity extends Activity {

	String RPassCode;
	EditText PassCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences pref = getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE); 
		RPassCode = pref.getString("RPasscode",null);
		if (RPassCode == null) {
			Intent intent = new Intent(this, RegistrationActivity.class);
			startActivity(intent);
		}
		
		PassCode = (EditText)findViewById(R.id.passcode);
	}
	
	public void Register(View v){
		Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
    }
	
	public void Login(View v){
		if(PassCode.getText().toString().equals(RPassCode)){			
			Intent intent = new Intent(this, TrackerActivity.class);
			startActivity(intent);
		}
    }
	
}
