package com.example.formularz;

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
import android.widget.Spinner;
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
}
