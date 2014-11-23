package com.cs442team4.medtrack;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.helper.SetReminders;
import com.cs442team4.medtrack.obj.Medicine;

@SuppressLint({ "NewApi", "DefaultLocale" })
public class CreateMedActivity extends Activity {
	
	MedList ML;
	EditText CreateMName, CreateMDes, CreateMCount, CreateMStartDate, CreateMTime1, CreateMTime2, CreateMTime3, CreateMTime4;
	CheckBox CreateMTime1Check, CreateMTime2Check, CreateMTime3Check, CreateMTime4Check;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_med);
		
		CreateMName = (EditText)findViewById(R.id.CreateMName);
		CreateMDes = (EditText)findViewById(R.id.CreateMDes);
		CreateMStartDate = (EditText)findViewById(R.id.CreateMStartDate);
		CreateMCount = (EditText)findViewById(R.id.CreateMCount);
		CreateMTime1 = (EditText)findViewById(R.id.CreateMTime1);
		CreateMTime2 = (EditText)findViewById(R.id.CreateMTime2);
		CreateMTime3 = (EditText)findViewById(R.id.CreateMTime3);
		CreateMTime4 = (EditText)findViewById(R.id.CreateMTime4);
		CreateMTime1Check = (CheckBox)findViewById(R.id.CreateMTime1Check);
		CreateMTime2Check = (CheckBox)findViewById(R.id.CreateMTime2Check);
		CreateMTime3Check = (CheckBox)findViewById(R.id.CreateMTime3Check);
		CreateMTime4Check = (CheckBox)findViewById(R.id.CreateMTime4Check);
		
	}
	
	public void CreateMed(View v){
		
		Medicine M = new Medicine();
		M.NAME = CreateMName.getText().toString();
		M.DESCRIPTION = CreateMDes.getText().toString();
		M.STARTDATE = CreateMStartDate.getText().toString();
		try{
		M.COUNT = Integer.parseInt(CreateMCount.getText().toString());
		} catch(Exception ex){
			M.COUNT = 0;
		}
		if(CreateMTime1Check.isChecked())
		M.TIME1 = CreateMTime1.getText().toString();
		if(CreateMTime2Check.isChecked())
		M.TIME2 = CreateMTime2.getText().toString();
		if(CreateMTime3Check.isChecked())
		M.TIME3 = CreateMTime3.getText().toString();
		if(CreateMTime4Check.isChecked())
		M.TIME4 = CreateMTime4.getText().toString();
		
		
		ML = new MedList(this.getBaseContext());
		ML.openWritable();
		long id = ML.insertData(M);
		ML.close();
		M.MED_ID = (int)id;
		
		SetReminders.Set(this, M);
		
		Intent intent2 = new Intent(this, TrackerActivity.class);
		intent2.putExtra("from", "1");
		startActivity(intent2);
		this.finish();
    }
	


public void showDatePickerDialog(View v) {
    DialogFragment newFragment = new DatePickerFragment();
    newFragment.show(getFragmentManager(), "datePicker");
}

public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	final Calendar c = Calendar.getInstance();
    	int year = c.get(Calendar.YEAR);
    	int month = c.get(Calendar.MONTH);
    	int day = c.get(Calendar.DAY_OF_MONTH);
    	
        EditText et = (EditText)getActivity().findViewById(R.id.CreateMStartDate);
        String time = et.getText().toString();
        if(!time.isEmpty()){
        	try {
        	String[] sp = time.split("/");
        	year = Integer.parseInt(sp[2]);
            month = Integer.parseInt(sp[1])-1;
            day = Integer.parseInt(sp[0]);
        	}catch(Exception ex){}
        }
		return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String a = String.format("%02d",day);
        String b = String.format("%02d",month + 1);
        String c = String.format("%04d",year);        
        EditText dateValue = (EditText)getActivity().findViewById(R.id.CreateMStartDate);
        dateValue.setText(a + "/" + b + "/" + c);
    }
}

public void showTimePickerDailog(View v){	
	DialogFragment newFragment = new TimePickerFragment(v.getId());
    newFragment.show(getFragmentManager(), "timePicker");
}

public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

	int ID;
	int CheckID;
	public TimePickerFragment(int id){
		ID = id;
		switch (ID) {
		case R.id.CreateMTime1:
			CheckID = R.id.CreateMTime1Check;
			break;
		case R.id.CreateMTime2:
			CheckID = R.id.CreateMTime2Check;
			break;
		case R.id.CreateMTime3:
			CheckID = R.id.CreateMTime3Check;
			break;
		case R.id.CreateMTime4:
			CheckID = R.id.CreateMTime4Check;
			break;
		default:
			CheckID = R.id.CreateMTime1Check;
			break;
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int hour = 0;
    	int min = 0;
		EditText et = (EditText)getActivity().findViewById(ID);
        String time = et.getText().toString();
        if(!time.isEmpty()){
        	try {
        	String[] sp = time.split(":");
        	hour = Integer.parseInt(sp[0]);
        	min = Integer.parseInt(sp[1]);
        	}catch(Exception ex){}
        }else{
        	final Calendar c = Calendar.getInstance();
        	hour = c.get(Calendar.HOUR_OF_DAY);
        	min = c.get(Calendar.MINUTE);
        }
		return new TimePickerDialog(getActivity(),this,hour,min,true);
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		String a = String.format("%02d",hourOfDay);
        String b = String.format("%02d",minute);       
        EditText dateValue = (EditText)getActivity().findViewById(ID);
        dateValue.setText(a + ":" + b);
        CheckBox cb =(CheckBox)getActivity().findViewById(CheckID);
        cb.setChecked(true);
	}
	
	
}

@Override
public void onBackPressed() {
	Intent intent = new Intent(this, TrackerActivity.class);
	startActivity(intent);
	this.finish();
	super.onBackPressed();
}
}
