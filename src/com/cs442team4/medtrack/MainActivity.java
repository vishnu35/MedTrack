package com.cs442team4.medtrack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cs442team4.medtrack.R;

public class MainActivity extends Activity {

	String RPassCode, PassCode = "";
	ImageView PasscodeImg;
	int passPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"UserDetails", MODE_PRIVATE);
		RPassCode = pref.getString("RPasscode", null);
		if (RPassCode == null) {
			Intent intent = new Intent(this, RegistrationActivity.class);
			startActivity(intent);
		}

		PasscodeImg = (ImageView) findViewById(R.id.passcodeimg);
	}

	public void passCodeBtnClick(View v) {
		if (passPosition < 4) {
			passPosition = passPosition + 1;
			Button key = (Button) findViewById(v.getId());
			PassCode = PassCode + key.getText().toString();
			int drawableResourceId = this.getResources().getIdentifier(
					"imgp" + passPosition, "drawable", this.getPackageName());
			PasscodeImg.setImageResource(drawableResourceId);

			if (passPosition == 4) {
				if (PassCode.equals(RPassCode)) {
					Intent intent = new Intent(this, TrackerActivity.class);
					startActivity(intent);
					finish();
				} else {
					passPosition = 0;
					PassCode = "";
					Handler handler = new Handler(); 
				    handler.postDelayed(new Runnable() { 
				         public void run() { PasscodeImg.setImageResource(R.drawable.imgp); 
				         } 
				    }, 500);
				}
			}
		} else {
			passPosition = 0;
			PassCode = "";
			PasscodeImg.setImageResource(R.drawable.imgp);
		}
	}

}
