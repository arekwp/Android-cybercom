package com.example.restfulclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.restfulclient.helpers.MyApplication;

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
	    default:
		return super.onOptionsItemSelected(item);
	}
    }
    
    @Override
    public void onClick(View v)
    {
	
    }
}
