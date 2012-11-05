package com.example.formularz;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.LinkMovementMethod;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EditText etSurname = (EditText) findViewById(R.id.etSurname);
		registerForContextMenu(etSurname);

		ArrayAdapter<String> adapterColours = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, COLOURS);

		ArrayAdapter<CharSequence> adapterLangs = ArrayAdapter
				.createFromResource(this, R.array.langs_array,
						android.R.layout.simple_dropdown_item_1line);

		ArrayAdapter<CharSequence> adapterGenders = ArrayAdapter
				.createFromResource(this, R.array.genders_array,
						android.R.layout.simple_spinner_item);

		Spinner spinner = (Spinner) findViewById(R.id.sGender);

		spinner.setAdapter(adapterGenders);

		MultiAutoCompleteTextView mactvLangs = 
				(MultiAutoCompleteTextView) findViewById(R.id.mactvLang);

		mactvLangs.setAdapter(adapterLangs);
		mactvLangs.setTokenizer(
				new MultiAutoCompleteTextView.
				CommaTokenizer());
		

		MultiAutoCompleteTextView mactvColours = 
				(MultiAutoCompleteTextView) findViewById(R.id.mactvColour);

		mactvColours.setAdapter(adapterColours);

		mactvColours
				.setTokenizer(
						new MultiAutoCompleteTextView
						.CommaTokenizer());
		
		EditText etTemp = (EditText)findViewById(R.id.etBlog);
		etTemp.setMovementMethod(LinkMovementMethod.getInstance());
		
		etTemp = (EditText)findViewById(R.id.etPhone);
		etTemp.setMovementMethod(LinkMovementMethod.getInstance());

		SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar1);
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		seekBar.setMax(y - 2004);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				TextView tv = (TextView)findViewById(R.id.tvOnFbValue);
				//tv.setText(seekBar.getVisibility());
				tv.setText(progress + "lat");
				//tv.setText(seekBar.getProgress());
				
			}
		});
	}

	private static final String[] COLOURS = new String[] { "niebieski",
			"czerwony", "czarny", "¿ó³ty", "zielony" };

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Dzia³ania");
		menu.add(0, v.getId(), 0, "Zamien na duze litery");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		EditText etSurname = (EditText) findViewById(R.id.etSurname);

		Editable edit = etSurname.getText();

		etSurname.setText(edit.toString().toUpperCase());
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menuClear:
			doClean();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void doClean() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.globalLinear);

		for (int i = 0; i < layout.getChildCount(); i++) {
			LinearLayout l = (LinearLayout) layout.getChildAt(i);
			for (int j = 0; j < l.getChildCount(); j++) {
				View v = l.getChildAt(j);
				if (v instanceof EditText) {
					((EditText) v).setText("");
				}

				if (v instanceof ToggleButton) {
					((ToggleButton) v).setChecked(false);
				}
			}
		}

		layout.invalidate();
	}
	
	public void onToggleClick(View view)
	{
	    // Is the toggle on?
	    boolean on = ((ToggleButton) view).isChecked();
	    LinearLayout ll = (LinearLayout)findViewById(R.id.fbLayout);
	    if (on) {
	    	ll.setVisibility(0);
	    } else {
	        ll.setVisibility(8);
	    }
	}
	
}
