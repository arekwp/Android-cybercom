package com.example.restfulclient;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.restfulclient.helpers.Category;
import com.example.restfulclient.helpers.MyApplication;

public class CategoryListActivity extends ListActivity
{
    MyApplication myApp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_category_list);
	
	myApp = (MyApplication) getApplication();
	
	setListAdapter(new BaseAdapter()
	{
	    
	    public View getView(int pos, View view, ViewGroup parent)
	    {
		if (view == null)
		{
		    view = View.inflate(CategoryListActivity.this,
			    android.R.layout.two_line_list_item, null);
		}
		
		Category cat = (Category) getItem(pos);
		
		TextView text = (TextView) view
		        .findViewById(android.R.id.text1);
		text.setText(cat.getCategoryId());
		
		text = (TextView) view.findViewById(android.R.id.text2);
		text.setText(cat.getCategoryName());
		return view;
	    }
	    
	    public long getItemId(int position)
	    {
		return position;
	    }
	    
	    public Object getItem(int position)
	    {
		return myApp.categories.get(position);
	    }
	    
	    public int getCount()
	    {
		return myApp.categories.size();
	    }
	});
	
	ListView lv = (ListView) findViewById(android.R.id.list);
	Log.d("number of data in lv: ", String.valueOf(lv.getCount()));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_category_list, menu);
	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	switch (item.getItemId())
	{
	    default:
		return super.onOptionsItemSelected(item);
	}
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
	// super.onListItemClick(l, v, position, id);
	Category selection = (Category) l.getItemAtPosition(position);
	Log.d("kliknieto: ", selection.getCategoryId());
    }
    
}
