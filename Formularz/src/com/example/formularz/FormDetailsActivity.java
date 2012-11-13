package com.example.formularz;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.Helpers.DatabaseHelper;
import com.example.Helpers.DatePickerFragment;
import com.example.Helpers.FormData;

public class FormDetailsActivity extends FragmentActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
	setContentView(R.layout.activity_form_details);
	
	
	Button b = (Button)findViewById(R.id.bShowDatePickerDialog);
	b.setOnClickListener(new View.OnClickListener()
	{
	    
	    public void onClick(View v)
	    {
		showDatePicker();
	    }
	});
	
	setViewsVisible(false);
	
	getData();
    }
    
    protected void showDatePicker()
    {
	DialogFragment df = new DatePickerFragment();
	DatePickerFragment.activity = "FormDetailsActivity";
	
	df.show(getSupportFragmentManager(), "datePicker");
	
    }
    
    private void getData()
    {
	Bundle extras = getIntent().getExtras();
	DatabaseHelper dh = new DatabaseHelper(this);
	setViewsContent(dh.getFormById(extras.getInt("form_id")));
    }
    
    /*
     * TODO dokonczyc ustawianie danych ponizej
     * 
     */
    private void setViewsContent(FormData f)
    {
	EditText et;
	et = (EditText) findViewById(R.id.etName);
	et.setText(f.getName());
	
	et = (EditText) findViewById(R.id.etSurname);
	et.setText(f.getSurname());
	
	et = (EditText) findViewById(R.id.etDesc);
	et.setText(f.getDescription());
	
	et = (EditText) findViewById(R.id.etBlog);
	et.setText(f.getBlog());
	
	et = (EditText) findViewById(R.id.etPhone);
	et.setText(f.getPhone());
	
    }
    
    private void setViewsVisible(boolean b)
    {
	LinearLayout layout = (LinearLayout) findViewById(R.id.global_linear);
	
	for (int i = 0; i < layout.getChildCount(); i++)
	{
	    LinearLayout l = (LinearLayout) layout.getChildAt(i);
	    for (int j = 0; j < l.getChildCount(); j++)
	    {
		View v = l.getChildAt(j);
		v.setEnabled(b);
	    }
	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_form_details, menu);
	return true;
    }
    
}
