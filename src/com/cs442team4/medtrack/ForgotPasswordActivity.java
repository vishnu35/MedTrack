package com.cs442team4.medtrack;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.appcompat.R.id;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ForgotPasswordActivity extends Activity {

	Button  getpass;
	EditText username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		username = (EditText)findViewById(R.id.FPUserName);
	}
	
	public void getPasswordBtnClick(View v){
		String uname = username.getText().toString();
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"UserDetails", MODE_PRIVATE);
		String RPassCode = pref.getString("RPasscode", null);
		String RUsername = pref.getString("RUserName", null);
		if (!uname.isEmpty()){
			if(RUsername.equals(uname)){
		    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
			builder.setMessage("Your Password : " + RPassCode);
			
			builder.setCancelable(false);
			builder.setNegativeButton("OK",
			        new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int whichButton) {
			            	finish();
			            }
			        });
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
			
			TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
			messageView.setGravity(Gravity.CENTER);
			}else{
				Toast.makeText(this, "Username doesnot match!!", Toast.LENGTH_LONG).show();
			}
			
		}else{
			Toast.makeText(this, "Please enter username", Toast.LENGTH_LONG).show();
		}
	}
}
