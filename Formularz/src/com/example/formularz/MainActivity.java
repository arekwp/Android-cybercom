package com.example.formularz;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.util.Linkify;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.Helpers.ContentHelper;
import com.example.Helpers.DatabaseHelper;
import com.example.Helpers.DateHelper;
import com.example.Helpers.DatePickerFragment;
import com.example.Helpers.FormData;

public class MainActivity extends FragmentActivity implements
        DatePickerDialog.OnDateSetListener
{
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	this.getWindow().setSoftInputMode(
	        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	
	setContentView(R.layout.activity_main);
	
	EditText etSurname = (EditText) findViewById(R.id.etSurname);
	registerForContextMenu(etSurname);
	
	ArrayAdapter<String> adapterColours = new ArrayAdapter<String>(this,
	        android.R.layout.simple_dropdown_item_1line, ContentHelper.COLOURS);
	
	ArrayAdapter<CharSequence> adapterLangs = ArrayAdapter
	        .createFromResource(this, R.array.langs_array,
	                android.R.layout.simple_dropdown_item_1line);
	
	ArrayAdapter<CharSequence> adapterGenders = new ArrayAdapter<CharSequence>
	(this, android.R.layout.simple_dropdown_item_1line, ContentHelper.GENDERS);
	
	Spinner spinner = (Spinner) findViewById(R.id.sGender);
	
	spinner.setAdapter(adapterGenders);
	
	MultiAutoCompleteTextView mactvLangs = (MultiAutoCompleteTextView) findViewById(R.id.mactvLang);
	
	mactvLangs.setAdapter(adapterLangs);
	mactvLangs.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	
	MultiAutoCompleteTextView mactvColours = (MultiAutoCompleteTextView) findViewById(R.id.mactvColour);
	
	mactvColours.setAdapter(adapterColours);
	
	mactvColours
	        .setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	
	EditText etTemp = (EditText) findViewById(R.id.etBlog);
	etTemp.setLinksClickable(true);
	etTemp.setAutoLinkMask(Linkify.ALL);
	
	etTemp = (EditText) findViewById(R.id.etPhone);
	etTemp.setLinksClickable(true);
	etTemp.setAutoLinkMask(Linkify.ALL);
	
	SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
	Calendar c = Calendar.getInstance();
	int y = c.get(Calendar.YEAR);
	seekBar.setMax(y - 2004);
	seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
	{
	    
	    public void onStopTrackingTouch(SeekBar seekBar)
	    {
	    }
	    
	    public void onStartTrackingTouch(SeekBar seekBar)
	    {
	    }
	    
	    public void onProgressChanged(SeekBar seekBar, int progress,
		    boolean fromUser)
	    {
		
		TextView tv = (TextView) findViewById(R.id.tvOnFbValue);
		tv.setText(progress + "lat");
	    }
	});
	
	TextView tvDate = (TextView) findViewById(R.id.tvDate);
	tvDate.setText(c.get(Calendar.DAY_OF_MONTH) + "-"
	        + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR)
	        + " ");
	
	Button b = (Button) findViewById(R.id.bShowDatePickerDialog);
	b.setOnClickListener(new View.OnClickListener()
	{
	    
	    public void onClick(View v)
	    {
		showDatePicker();
	    }
	});
    }
    
    protected void showDatePicker()
    {
	Calendar c = Calendar.getInstance();
	DialogFragment df = new DatePickerFragment();
	DatePickerFragment.YEAR = c.get(Calendar.YEAR);
	DatePickerFragment.MONTH = c.get(Calendar.MONTH);
	DatePickerFragment.DAY = c.get(Calendar.DAY_OF_MONTH);
	
	DatePickerFragment.activity = "MainActivity";
	
	df.show(getSupportFragmentManager(), "datePicker");
	
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo)
    {
	super.onCreateContextMenu(menu, v, menuInfo);
	menu.setHeaderTitle("Dzia³ania");
	menu.add(0, v.getId(), 0, "Zamien na duze litery");
    }
    
    @SuppressLint("DefaultLocale")
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
	
	EditText etSurname = (EditText) findViewById(R.id.etSurname);
	
	Editable edit = etSurname.getText();
	
	etSurname.setText(edit.toString().toUpperCase());
	return true;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	getMenuInflater().inflate(R.menu.activity_main, menu);
	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	// Handle item selection
	switch (item.getItemId())
	{
	    case R.id.menu_clear_form:
		doClean();
		return true;
	    case R.id.menu_erase_db:
		eraseDb();
		return true;
	    case R.id.menu_add_form:
		addFormToSQLite();
		doClean();
		return true;
	    case R.id.menu_goto_form_list:
		Intent intent = new Intent(MainActivity.this,
		        FormListActivity.class);
		MainActivity.this.startActivity(intent);
		return true;
	    default:
		return super.onOptionsItemSelected(item);
	}
    }
    
    private void eraseDb()
    {
	DatabaseHelper dh = new DatabaseHelper(this);
	
	dh.dropDb();
	
	dh.close();
	
    }
    
    private void addFormToSQLite()
    {
	FormData fd = packData();
	
	DatabaseHelper dh = new DatabaseHelper(this);
	
	Log.d("Dodaje nowy formularz: ", fd.getName() + " " + fd.getSurname());
	dh.addForm(fd);
	Log.d("Ilosc rekordow: ", String.valueOf(dh.getCount()));
	dh.close();
	
    }
    
    private void doClean()
    {
	LinearLayout layout = (LinearLayout) findViewById(R.id.global_linear);
	
	for (int i = 0; i < layout.getChildCount(); i++)
	{
	    LinearLayout l = (LinearLayout) layout.getChildAt(i);
	    for (int j = 0; j < l.getChildCount(); j++)
	    {
		View v = l.getChildAt(j);
		if (v instanceof EditText)
		{
		    ((EditText) v).setText("");
		}
		
		if (v instanceof ToggleButton)
		{
		    ((ToggleButton) v).setChecked(false);
		}
		
		if (v instanceof SeekBar)
		{
		    ((SeekBar) v).setProgress(0);
		}
		
		if (v.getId() == R.id.tvDate)
		{
		    ((TextView) v).setText("");
		}
	    }
	}
	
	layout.invalidate();
    }
    
    public void onToggleClick(View view)
    {
	boolean on = ((ToggleButton) view).isChecked();
	LinearLayout ll = (LinearLayout) findViewById(R.id.fbLayout);
	if (on)
	{
	    ll.setVisibility(0);
	} else
	{
	    ll.setVisibility(8);
	}
    }
    
    /**
     * Zbiera wszystkie dane z formularza i zapisuje je do obiektu klasy
     * FormData;
     * 
     * @return
     */
    private FormData packData()
    {
	FormData fd;
	String name = ((EditText) findViewById(R.id.etName)).getText()
	        .toString();
	String surname = ((EditText) findViewById(R.id.etSurname)).getText()
	        .toString();
	String description = ((EditText) findViewById(R.id.etDesc)).getText()
	        .toString();
	String blog = ((EditText) findViewById(R.id.etBlog)).getText()
	        .toString();
	String languages = ((MultiAutoCompleteTextView) findViewById(R.id.mactvLang))
	        .getText().toString();
	String colours = ((MultiAutoCompleteTextView) findViewById(R.id.mactvColour))
	        .getText().toString();
	String phone = ((EditText) findViewById(R.id.etPhone)).getText()
	        .toString();
	String gender = ((Spinner) findViewById(R.id.sGender))
	        .getSelectedItem().toString();
	String birthDate = ((TextView) findViewById(R.id.tvDate)).
		getText().toString();
	int doHaveFbAcc = ((ToggleButton) findViewById(R.id.toggleButton1))
	        .isChecked() ? 1 : 0;
	int doHaveFbSince = ((SeekBar) findViewById(R.id.seekBar1))
	        .getProgress();
	
	fd = new FormData(name, surname, description, blog, languages, colours,
	        birthDate, phone, gender, doHaveFbAcc, doHaveFbSince);
	return fd;
    }
    
    public void onButtonClick(View view)
    {
	AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	alertDialog.setTitle("Informacja");
	alertDialog.setMessage("Dziêkujemy za wype³nienie folmularza");
	alertDialog.show();
    }
    
    public void onEditClick(View view)
    {
	EditText etBlog = (EditText) findViewById(R.id.etBlog);
	Linkify.addLinks(etBlog, Linkify.WEB_URLS);
    }
    
    public void onPhoneClick(View view)
    {
	EditText etPhone = (EditText) findViewById(R.id.etPhone);
	Linkify.addLinks(etPhone, Linkify.PHONE_NUMBERS);
    }
    
    public void onDateSet(DatePicker view, int year, int month, int day)
    {
	TextView tvDate = (TextView) findViewById(R.id.tvDate);
	
	Log.d("wybrana data: ", String.valueOf(day + "-" + month + "-" + year));
	if (DateHelper.isNoFuture(day, month, year))
	    
	    tvDate.setText(day + "-" + (month+1) + "-" + year + " ");
	else
	{
	    showDatePicker();
	    Toast.makeText(this, "Data wybiega w przyszlosc", Toast.LENGTH_LONG).show();
	}
	
    }
    
}
