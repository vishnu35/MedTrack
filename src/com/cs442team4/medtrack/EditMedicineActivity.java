package com.cs442team4.medtrack;

import java.util.Calendar;

import javax.xml.datatype.Duration;

import com.cs442team4.medtrack.CreateMedActivity.DatePickerFragment;
import com.cs442team4.medtrack.CreateMedActivity.TimePickerFragment;
import com.cs442team4.medtrack.db.MedList;
import com.cs442team4.medtrack.obj.Medicine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint({ "NewApi", "DefaultLocale" })
public class EditMedicineActivity extends Activity {

	long id;
	MedList ML;
	EditText EditMName, EditMDes, EditMCount, EditMStartDate, EditMTime1, EditMTime2, EditMTime3, EditMTime4;
	CheckBox EditMTime1Check, EditMTime2Check, EditMTime3Check, EditMTime4Check;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_medicine);
		
		EditMName = (EditText)findViewById(R.id.EditMName);
		EditMDes = (EditText)findViewById(R.id.EditMDes);
		EditMStartDate = (EditText)findViewById(R.id.EditMStartDate);
		EditMCount = (EditText)findViewById(R.id.EditMCount);
		EditMTime1 = (EditText)findViewById(R.id.EditMTime1);
		EditMTime2 = (EditText)findViewById(R.id.EditMTime2);
		EditMTime3 = (EditText)findViewById(R.id.EditMTime3);
		EditMTime4 = (EditText)findViewById(R.id.EditMTime4);
		

		EditMTime1Check = (CheckBox)findViewById(R.id.EditMTime1Check);
		EditMTime2Check = (CheckBox)findViewById(R.id.EditMTime2Check);
		EditMTime3Check = (CheckBox)findViewById(R.id.EditMTime3Check);
		EditMTime4Check = (CheckBox)findViewById(R.id.EditMTime4Check);
		
		Intent mIntent = getIntent();
		id = mIntent.getLongExtra("id", 0);
		//id = getIntent().getExtras().getInt("id");
		//Toast.makeText(this, "id:"+intValue,Toast.LENGTH_LONG).show();
		
		MedList ML = new MedList(this.getBaseContext());
		ML.openReadable();
		Medicine md = ML.getMedDetailsObj(id);
		ML.close();
		
		EditMName.setText(md.NAME);
		EditMDes.setText(md.DESCRIPTION);
		EditMStartDate.setText(md.STARTDATE);
		EditMCount.setText(""+md.COUNT);
		EditMTime1.setText(md.TIME1);
		EditMTime2.setText(md.TIME2);
		EditMTime3.setText(md.TIME3);
		EditMTime4.setText(md.TIME4);
		EditMTime1Check.setChecked(md.TIME1 != null && !md.TIME1.isEmpty());
		EditMTime2Check.setChecked(md.TIME2 != null && !md.TIME2.isEmpty());	
		EditMTime3Check.setChecked(md.TIME3 != null && !md.TIME3.isEmpty());	
		EditMTime4Check.setChecked(md.TIME4 != null && !md.TIME4.isEmpty());
	}

public void EditMed(View v){
	Medicine M = new Medicine();
	M.MED_ID = (int)id;
	M.NAME = EditMName.getText().toString();
	M.DESCRIPTION = EditMDes.getText().toString();
	M.STARTDATE = EditMStartDate.getText().toString();
	try{
	M.COUNT = Integer.parseInt(EditMCount.getText().toString());
	} catch(Exception ex){
		M.COUNT = 0;
	}
	if(EditMTime1Check.isChecked())
	M.TIME1 = EditMTime1.getText().toString();
	if(EditMTime2Check.isChecked())
	M.TIME2 = EditMTime2.getText().toString();
	if(EditMTime3Check.isChecked())
	M.TIME3 = EditMTime3.getText().toString();
	if(EditMTime4Check.isChecked())
	M.TIME4 = EditMTime4.getText().toString();
	
	
	ML = new MedList(this.getBaseContext());
	ML.openWritable();
	boolean edit = ML.editMed(M);
	ML.close();
	
	if(edit){
	Intent intent = new Intent(this, TrackerActivity.class);
	intent.putExtra("from", "1");
	startActivity(intent);
	}
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
    	
        EditText et = (EditText)getActivity().findViewById(R.id.EditMStartDate);
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
        EditText dateValue = (EditText)getActivity().findViewById(R.id.EditMStartDate);
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
        }
		return new TimePickerDialog(getActivity(),this,hour,min,true);
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		String a = String.format("%02d",hourOfDay);
        String b = String.format("%02d",minute);       
        EditText dateValue = (EditText)getActivity().findViewById(ID);
        dateValue.setText(a + ":" + b);
	}
	
}
}
