package com.cs442team4.medtrack.helper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cs442team4.medtrack.EditMedicineActivity;
import com.cs442team4.medtrack.MedicineActivity;
import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.TrackerActivity;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.obj.Medicine;

@SuppressLint("InflateParams")
public class DailogMedicineDetails{   
	static MedList ML;
	static MedicineActivity context;
	static Medicine md;
	static Dialog dialog;
    public static void app_launched(MedicineActivity mContext, final long id) {
    	context = mContext;    	
    	dialog = new Dialog(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.meddetails, null);
		TextView MedName = (TextView)v.findViewById(R.id.MedDName);
		TextView MedDesc = (TextView)v.findViewById(R.id.MedDDesc);
		TextView MedCount = (TextView)v.findViewById(R.id.MedDCount);
		TextView MedDate = (TextView)v.findViewById(R.id.MedDSDate);
		TextView MedTimings = (TextView)v.findViewById(R.id.MedDTimings);
		Button BtnClose = (Button) v.findViewById(R.id.MedDBtnClose);
		Button BtnDelete = (Button) v.findViewById(R.id.MedDBtnDelete);
		Button BtnEdit = (Button) v.findViewById(R.id.MedDBtnEdit);
		
		ML = new MedList(mContext);
		ML.openReadable();
		md = ML.getMedDetailsObj(id);
		ML.close();
			MedName.setText(md.NAME);
			MedDesc.setText("Description : "+md.DESCRIPTION);
			MedCount.setText("Count : "+ md.COUNT);
			MedDate.setText("StartDate : "+md.STARTDATE);
			MedTimings.setText("Timings : "+MedList.getTimings(md.TIME1, md.TIME2, md.TIME3, md.TIME4));	
        
		BtnClose.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
		
		BtnDelete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //dialog.dismiss();
                DeleteMed(id);
                SetReminders.Remove(context, md);
            }
        });
		
		BtnEdit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMedicineActivity.class);
                intent.putExtra("id", id);
				context.startActivity(intent);
                dialog.dismiss();
                context.finish();
            }
        });			    
		
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v); 
		dialog.setCancelable(false);
        dialog.show();
    }
    
    public static void DeleteMed(final long id){
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("Are you sure u want to delete this medicine?");
		
		builder.setCancelable(false);
		builder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0,int arg1) {
						ML = new MedList(context);
						ML.openWritable();
						if(ML.deleteMed(id)){
						Intent intent = new Intent(context, TrackerActivity.class);
						context.startActivity(intent);
						dialog.dismiss();
						context.finish();
						}
						ML.close();
					}
				});
		builder.setNegativeButton("No",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            }
		        });
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		
		TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);
    	
    }
}
