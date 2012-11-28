package com.example.formularz;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.util.Linkify;
import android.util.Log;
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

import com.example.helpers.ContentHelper;
import com.example.helpers.DatabaseHelper;
import com.example.helpers.DateHelper;
import com.example.helpers.DatePickerFragment;
import com.example.helpers.FormData;

public class FormDetailsActivity extends FragmentActivity implements
        DatePickerDialog.OnDateSetListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	this.getWindow().setSoftInputMode(
	        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	setContentView(R.layout.activity_form_details);

	MenuItem mi = (MenuItem) findViewById(R.id.menu_save);
	mi.setEnabled(false);

	EditText etSurname = (EditText) findViewById(R.id.etSurname);
	registerForContextMenu(etSurname);

	ArrayAdapter<String> adapterColours = new ArrayAdapter<String>(this,
	        android.R.layout.simple_dropdown_item_1line,
	        ContentHelper.COLOURS);

	ArrayAdapter<CharSequence> adapterLangs = ArrayAdapter
	        .createFromResource(this, R.array.langs_array,
	                android.R.layout.simple_dropdown_item_1line);

	ArrayAdapter<CharSequence> adapterGenders = new ArrayAdapter<CharSequence>(
	        this, android.R.layout.simple_dropdown_item_1line,
	        ContentHelper.GENDERS);

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
     * TODO dokonczyc ustawianie danych ponizej date ustawic poprzez statyczne
     * pola YEAR, MONTH, DAY
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
	Log.d("wyrane id plec - " + f.getGender() + ": ",
	        String.valueOf(ContentHelper.getId(ContentHelper.GENDERS,
	                f.getGender())));
	s.setSelection(ContentHelper.getId(ContentHelper.GENDERS, f.getGender()));

	// Facebook
	ToggleButton tbOnFacebook = (ToggleButton) findViewById(R.id.toggleButton1);
	if (f.getDoHaveFbAcc() == 1)
	{
	    tbOnFacebook.performClick();

	    SeekBar sb = (SeekBar) findViewById(R.id.seekBar1);
	    sb.setProgress(f.getDoHaveFbSince());

	    TextView tvTemp = (TextView) findViewById(R.id.tvOnFbValue);
	    tvTemp.setText(sb.getProgress() + "lat");
	}
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
		MenuItem miEdit = (MenuItem) findViewById(R.id.menu_edit);
		miEdit.setEnabled(false);
		return true;
	    case R.id.menu_save:
		doSaveToDB();
		return true;
	    default:
		return super.onOptionsItemSelected(item);
	}
    }

    private void doSaveToDB()
    {
	FormData fd = packData();
	DatabaseHelper dh = new DatabaseHelper(this);
	
	dh.update(fd);
    }

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
	String birthDate = ((TextView) findViewById(R.id.tvDate)).getText()
	        .toString();
	int doHaveFbAcc = ((ToggleButton) findViewById(R.id.toggleButton1))
	        .isChecked() ? 1 : 0;
	int doHaveFbSince = ((SeekBar) findViewById(R.id.seekBar1))
	        .getProgress();

	fd = new FormData(name, surname, description, blog, languages, colours,
	        birthDate, phone, gender, doHaveFbAcc, doHaveFbSince);
	return fd;
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
	TextView tvDate = (TextView) findViewById(R.id.tvDate);

	if (DateHelper.isNoFuture(day, month, year))

	    tvDate.setText(day + "-" + (++month) + "-" + year + " ");
	else
	{
	    showDatePicker();
	    Toast.makeText(this, "Data wybiega w przyszlosc", Toast.LENGTH_LONG)
		    .show();
	}

    }
}
