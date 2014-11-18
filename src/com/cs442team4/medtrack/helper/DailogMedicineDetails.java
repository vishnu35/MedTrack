package com.cs442team4.medtrack.helper;

import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.db.MedList;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DailogMedicineDetails{   
	static MedList ML;
    public static void app_launched(Context mContext, long id) {
    	    	
    	final Dialog dialog = new Dialog(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.meddetails, null);
		TextView MedName = (TextView)v.findViewById(R.id.MedDName);
		TextView MedDesc = (TextView)v.findViewById(R.id.MedDDesc);
		Button BtnClose = (Button) v.findViewById(R.id.MedDBtnClose);
		
		ML = new MedList(mContext);
		ML.openReadable();
		Cursor cursor = ML.getMedDetails(id);
		while(cursor.moveToNext())
		{
			MedName.setText(cursor.getString(cursor.getColumnIndex(MedList.NAME)));
			MedDesc.setText(cursor.getString(cursor.getColumnIndex(MedList.DESCRIPTION)));			
		}
		cursor.close();
        
		BtnClose.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
		
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v); 
		dialog.setCancelable(false);
        dialog.show(); 
    } 
}
// see http://androidsnippets.com/prompt-engaged-users-to-rate-your-app-in-the-android-market-appirater
