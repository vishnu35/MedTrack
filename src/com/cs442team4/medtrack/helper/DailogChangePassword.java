package com.cs442team4.medtrack.helper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.SettingsActivity;

@SuppressLint({ "InflateParams", "NewApi" })
public class DailogChangePassword {
	static SettingsActivity context;
	static TextView OldPass, NewPass, NewRPass;

	public static void app_launched(SettingsActivity mContext) {
		context = mContext;
		final Dialog dialog = new Dialog(mContext);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.changepassword, null);
		OldPass = (TextView) v.findViewById(R.id.CPOldPasscode);
		NewPass = (TextView) v.findViewById(R.id.CPNewPasscode);
		NewRPass = (TextView) v.findViewById(R.id.CPNewRPasscode);

		Button BtnClose = (Button) v.findViewById(R.id.CPbtnCancel);
		BtnClose.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		Button BtnChange = (Button) v.findViewById(R.id.CPbtnChange);
		BtnChange.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String oldp = OldPass.getText().toString();
				String newp = NewPass.getText().toString();
				String newrp = NewRPass.getText().toString();
				if (!oldp.isEmpty() && !newp.isEmpty() && !newrp.isEmpty()) {
					SharedPreferences pref = context.getApplicationContext()
							.getSharedPreferences("UserDetails",
									Context.MODE_PRIVATE);
					if (!oldp.equals(pref.getString("RPasscode", null))) {
						Toast.makeText(context, "Old Passcode Doesnot Match",
								Toast.LENGTH_LONG).show();
					} else if (!newp.equals(newrp)) {
						Toast.makeText(context, "New passcode Doesnot Match",
								Toast.LENGTH_LONG).show();
					} else {
						Editor editor = pref.edit();
						editor.putString("RPasscode", newp);
						editor.commit();
						Toast.makeText(context,
								"Passcode is been changed successfuly",
								Toast.LENGTH_LONG).show();
						dialog.dismiss();

					}
				} else {
					Toast.makeText(context, "Enter all details",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
		// WindowManager.LayoutParams.MATCH_PARENT);
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(v);
		dialog.setCancelable(false);
		dialog.show();
	}
}
