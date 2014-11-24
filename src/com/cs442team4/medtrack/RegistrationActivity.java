package com.cs442team4.medtrack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	EditText RUserName, RPasscode, RPasscodeRe;
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		RUserName = (EditText) findViewById(R.id.RUserName);
		RPasscode = (EditText) findViewById(R.id.RPasscode);
		RPasscodeRe = (EditText) findViewById(R.id.RPasscodeRe);
	}

	@SuppressLint("NewApi")
	public void CreateAccount(View v) {
		String username = RUserName.getText().toString();
		String pass = RPasscode.getText().toString();
		String passre = RPasscodeRe.getText().toString();
		if (!username.isEmpty() && !pass.isEmpty() && !passre.isEmpty()) {
			if (pass.equals(passre)) {

				SharedPreferences pref = getApplicationContext()
						.getSharedPreferences("UserDetails", MODE_PRIVATE);
				editor = pref.edit();
				editor.putString("RUserName", username);
				editor.putString("RPasscode", pass);
				editor.commit();
				Intent intent = new Intent(RegistrationActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			} else
				Toast.makeText(this, "Passcode does not match!", Toast.LENGTH_LONG).show();
		} else
			Toast.makeText(this, "Please fill all details!", Toast.LENGTH_LONG)
					.show();

	}
}
