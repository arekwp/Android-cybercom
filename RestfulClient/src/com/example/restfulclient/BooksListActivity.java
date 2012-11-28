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

import com.example.restfulclient.helpers.Book;
import com.example.restfulclient.helpers.Category;
import com.example.restfulclient.helpers.MyApplication;

public class BooksListActivity extends ListActivity
{
    
    MyApplication myApp = null;
    Category cat = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_books_list);
	
	myApp = (MyApplication) getApplication();
	int index = myApp.getById(myApp.catId);
	cat = myApp.categories.get(index);
	
	Log.d("odebrano id: ", myApp.catId);
	Log.d("wybrano kat: ", cat.getCategoryId());
	
	setListAdapter(new BaseAdapter()
	{
	    
	    @Override
	    public View getView(int pos, View view, ViewGroup parent)
	    {
		if (view == null)
		{
		    view = View.inflate(BooksListActivity.this,
			    android.R.layout.two_line_list_item, null);
		}
		
		Book cat = (Book) getItem(pos);
		
		TextView text = (TextView) view
		        .findViewById(android.R.id.text1);
		text.setText(cat.getBookName());
		
		text = (TextView) view.findViewById(android.R.id.text2);
		text.setText(cat.getAuthor());
		return view;
	    }
	    
	    @Override
	    public long getItemId(int position)
	    {
		return position;
	    }
	    
	    @Override
	    public Object getItem(int position)
	    {
		Log.d("getItem: ",
		        ((Book) cat.getBooks().toArray()[position]).getBookId());
		return (Book) cat.getBooks().toArray()[position];
	    }
	    
	    @Override
	    public int getCount()
	    {
		Log.d("getCount books: ", String.valueOf(cat.getBooks().size()));
		return cat.getBooks().size();
	    }
	});
	
	ListView lv = (ListView) findViewById(android.R.id.list);
	Log.d("number of data in lv: ", String.valueOf(lv.getCount()));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_books_list, menu);
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
	Book selection = (Book) l.getItemAtPosition(position);
	Log.d("kliknieto: ", selection.getAuthor());
    }
}
