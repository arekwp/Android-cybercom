package com.example.restfulclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.restfulclient.CategoryListActivity.GetCategoriesThread;
import com.example.restfulclient.helpers.Category;
import com.example.restfulclient.helpers.ILibraryDAO;
import com.example.restfulclient.helpers.MyApplication;
import com.example.restfulclient.helpers.OnlineLibrary;
import com.example.restfulclient.helpers.SQLiteLibrary;

public class CategoryDetailsActivity extends Activity implements
        OnClickListener
{
    MyApplication myApp;
    boolean editMode = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	Button button = (Button) findViewById(R.id.bSend);
	
	button.setOnClickListener(this);
	
	myApp = (MyApplication) getApplication();
	
	if (myApp.c != null)
	{
	    editMode = true;
	    
	    EditText etId = (EditText) findViewById(R.id.etCatId);
	    EditText etName = (EditText) findViewById(R.id.etCatName);
	    
	    etId.setText(myApp.c.getCategoryId());
	    etName.setText(myApp.c.getCategoryName());
	    
	    etId.setEnabled(false);
	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_main, menu);
	return true;
    }
    
    @Override
    public void onClick(View v)
    {
	new CategoryThread().execute();
	// Toast.makeText(getApplicationContext(), "Kat: " +
	// etId.getText().toString() + etName.getText().toString(),
	// Toast.LENGTH_LONG).show();
	
    }
    
    private class CategoryThread extends AsyncTask<Void, Void, Void>
    {
	
	@Override
	protected Void doInBackground(Void... params)
	{
	    ILibraryDAO library = null;
	    
	    if (myApp.isOffline())
		library = new SQLiteLibrary(CategoryDetailsActivity.this);
	    else
		library = new OnlineLibrary(myApp.addr);
	    
	    EditText etId = (EditText) findViewById(R.id.etCatId);
	    EditText etName = (EditText) findViewById(R.id.etCatName);
	    if (editMode)
	    {
		Log.v("calling", "updateCategory");
		library.updateCategory(new Category(etId.getText().toString(),
		        etName.getText().toString()));
	    } else
		library.addCategory(new Category(etId.getText().toString(),
		        etName.getText().toString()));
	    
	    return null;
	    
	}
	
	protected void onPostExecute(Void result)
	{
	    finish();
	}
    }
}
