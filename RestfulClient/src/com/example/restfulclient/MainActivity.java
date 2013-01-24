package com.example.restfulclient;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.restfulclient.helpers.ILibraryDAO;
import com.example.restfulclient.helpers.MyApplication;
import com.example.restfulclient.helpers.OnlineLibrary;
import com.example.restfulclient.helpers.SQLiteLibrary;

public class MainActivity extends Activity implements OnClickListener
{
	MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.bSend);

		button.setOnClickListener(this);

		myApp = (MyApplication) getApplication();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		switch (item.getItemId())
		{
			case R.id.menu_cat_list:
				Intent intent = new Intent(MainActivity.this,
				        CategoryListActivity.class);
				MainActivity.this.startActivity(intent);
				return true;
			case R.id.menu_work_mode:
				myApp.offline = !myApp.offline;
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onPrepareOptionsMenu (Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		if(myApp.offline)
			menu.findItem(R.id.menu_work_mode).setTitle("Go online");
		else
			menu.findItem(R.id.menu_work_mode).setTitle("Go offline");
		return true;
		
	}

	@Override
	public void onClick(View v)
	{
	    ILibraryDAO library = null;
	    if(myApp.offline)
		library = new SQLiteLibrary();
	    else
		library = new OnlineLibrary();
	    
	    
	}
}
