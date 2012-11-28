package com.example.formularz;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
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

import com.example.helpers.DatabaseHelper;
import com.example.helpers.FormData;

public class FormListActivity extends ListActivity
{
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_form_list);
	
	DatabaseHelper dh = new DatabaseHelper(this);
	
	Log.d("ilosc rekordow: ", String.valueOf(dh.getCount()));
	
	final List<FormData> form_list = dh.getAllFormData();
	dh.close();
	
	setListAdapter(new BaseAdapter()
	{
	    
	    public View getView(int pos, View view, ViewGroup parent)
	    {
		if (view == null)
		{
		    view = View.inflate(FormListActivity.this,
			    android.R.layout.two_line_list_item, null);
		}
		
		FormData form = (FormData) getItem(pos);
		
		TextView text = (TextView) view
		        .findViewById(android.R.id.text1);
		text.setText(form.getName());
		
		text = (TextView) view.findViewById(android.R.id.text2);
		text.setText(form.getSurname());
		return view;
	    }
	    
	    public long getItemId(int position)
	    {
		return position;
	    }
	    
	    public Object getItem(int position)
	    {
		return form_list.get(position);
	    }
	    
	    public int getCount()
	    {
		return form_list.size();
	    }
	});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	getMenuInflater().inflate(R.menu.activity_form_list, menu);
	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	// Handle item selection
	switch (item.getItemId())
	{
	    case R.id.menu_add_new_form:
		finish();
		return true;
	    default:
		return super.onOptionsItemSelected(item);
	}
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
	// super.onListItemClick(l, v, position, id);
	FormData selection = (FormData) l.getItemAtPosition(position);
	Intent intent = new Intent(FormListActivity.this,
	        FormDetailsActivity.class);
	intent.putExtra("form_id", selection.getId());
	FormListActivity.this.startActivity(intent);
    }
}
