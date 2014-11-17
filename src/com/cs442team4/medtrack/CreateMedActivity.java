package com.cs442team4.medtrack;

import java.util.Calendar;

import com.cs442team4.medtrack.R;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.obj.Medicine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

@SuppressLint({ "NewApi", "DefaultLocale" })
public class CreateMedActivity extends Activity {
	
	MedList ML;
	EditText CreateMName, CreateMDes, CreateMCount, CreateMStartDate, CreateMTime1, CreateMTime2, CreateMTime3, CreateMTime4;
	CheckBox CreateMTimecheckBox1 ,CreateMTimecheckBox2, CreateMTimecheckBox3, CreateMTimecheckBox4, CreateMInterval;
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
		
		CreateMTimecheckBox1 = (CheckBox)findViewById(R.id.CreateMTimecheckBox1);
		CreateMTimecheckBox2 = (CheckBox)findViewById(R.id.CreateMTimecheckBox2);
		CreateMTimecheckBox3 = (CheckBox)findViewById(R.id.CreateMTimecheckBox3);
		CreateMTimecheckBox4 = (CheckBox)findViewById(R.id.CreateMTimecheckBox4);
		CreateMInterval = (CheckBox)findViewById(R.id.CreateMInterval);
		
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
		M.TIME1 = CreateMTime1.getText().toString();
		M.TIME2 = CreateMTime2.getText().toString();
		M.TIME3 = CreateMTime3.getText().toString();
		M.TIME4 = CreateMTime4.getText().toString();
		
		M.TIME1CHECK = CreateMTimecheckBox1.isChecked() ? 1 : 0 ;
		M.TIME2CHECK = CreateMTimecheckBox2.isChecked() ? 1 : 0 ;
		M.TIME3CHECK = CreateMTimecheckBox3.isChecked() ? 1 : 0 ;
		M.TIME4CHECK = CreateMTimecheckBox4.isChecked() ? 1 : 0 ;
		M.REPEAT = CreateMInterval.isChecked() ? 1 : 0 ;
		
		ML = new MedList(this.getBaseContext());
		ML.openWritable();
		long id = ML.insertData(M);
		ML.close();
		
		Intent intent = new Intent(this, TrackerActivity.class);
		intent.putExtra("from", "1");
		startActivity(intent);
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
	public TimePickerFragment(int id){
		ID = id;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new TimePickerDialog(getActivity(),this,0,0,true);
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		String a = String.format("%02d",hourOfDay);
        String b = String.format("%02d",minute);       
        EditText dateValue = (EditText)getActivity().findViewById(ID);
        dateValue.setText(a + ":" + b);
	}
	
}
}
