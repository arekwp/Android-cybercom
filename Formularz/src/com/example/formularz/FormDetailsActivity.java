package com.example.formularz;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;

import com.example.Helpers.ContentHelper;
import com.example.Helpers.DatabaseHelper;
import com.example.Helpers.DateHelper;
import com.example.Helpers.DatePickerFragment;
import com.example.Helpers.FormData;

public class FormDetailsActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	this.getWindow().setSoftInputMode(
	        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	setContentView(R.layout.activity_form_details);

	Button b = (Button) findViewById(R.id.bShowDatePickerDialog);
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
     * date ustawic poprzez statyczne pola YEAR, MONTH, DAY
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
	
	TextView tv;
	tv = (TextView) findViewById(R.id.tvDate);
	tv.setText(f.getBirthDate());
	
	MultiAutoCompleteTextView mactv;
	mactv = (MultiAutoCompleteTextView) findViewById(R.id.mactvColour);
	mactv.setText(f.getColours());
	
	mactv = (MultiAutoCompleteTextView) findViewById(R.id.mactvLang);
	mactv.setText(f.getLanguages());
	
	Spinner s;
	s = (Spinner) findViewById(R.id.sGender);
	s.setSelection(ContentHelper.getId(ContentHelper.GENDERS, f.getGender()));
	

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	switch (item.getItemId())
	{
	    case R.id.menu_edit:
		setViewsVisible(true);
		return true;
	    default:
		return super.onOptionsItemSelected(item);
	}
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
	TextView tvDate = (TextView) findViewById(R.id.tvDate);
	
	if (DateHelper.isNoFuture(day, month, year))
	    
	    tvDate.setText(day + "-" + (++month) + "-" + year + " ");
	else
	{
	    showDatePicker();
	    Toast.makeText(this, "Data wybiega w przyszlosc", Toast.LENGTH_LONG).show();
	}
	
    }
}
