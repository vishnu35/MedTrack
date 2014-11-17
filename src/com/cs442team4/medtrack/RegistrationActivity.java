package com.cs442team4.medtrack;

import com.cs442team4.medtrack.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class RegistrationActivity extends Activity {

	EditText RUserName, RPasscode, RPasscodeRe;
	Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		RUserName = (EditText)findViewById(R.id.RUserName);
		RPasscode = (EditText)findViewById(R.id.RPasscode);		
		RPasscodeRe = (EditText)findViewById(R.id.RPasscodeRe);
	}
	
	public void CreateAccount(View v){
		SharedPreferences pref = getApplicationContext().getSharedPreferences("UserDetails", MODE_PRIVATE); 
		editor = pref.edit();		
		editor.putString("RUserName", RUserName.getText().toString());
		editor.putString("RPasscode", RPasscode.getText().toString());
		editor.commit();
		Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
		startActivity(intent);
    }
}
