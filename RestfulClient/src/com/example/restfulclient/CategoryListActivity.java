package com.example.restfulclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.restfulclient.helpers.Parser;

public class CategoryListActivity extends ListActivity
{
    MyApplication myApp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_category_list);
	
	new GetCategoriesThread().execute("192.168.1.2");
	
	myApp = (MyApplication) getApplication();
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
	myApp.c = selection;
	
	Intent i = new Intent(CategoryListActivity.this,
	        BooksListActivity.class);
	CategoryListActivity.this.startActivity(i);
    }
    
    class GetCategoriesThread extends AsyncTask<String, Void, List<Category>>
    {
	protected List<Category> doInBackground(String... url)
	{
	    String webClientUrl = "http://" + url[0] + ":8020/";
	    String categoryUrl = "categoryservice/category";
	    
	    List<Category> list = null;
	    
	    HttpParams params = new BasicHttpParams();
	    
	    HttpConnectionParams.setConnectionTimeout(params, 2000);
	    HttpConnectionParams.setSoTimeout(params, 2000);
	    
	    HttpClient hc = new DefaultHttpClient(params);
	    
	    HttpGet hg = new HttpGet(webClientUrl + categoryUrl);
	    try
	    {
		HttpResponse hr = hc.execute(hg);
		
		HttpEntity entity = hr.getEntity();
		
		if (entity != null)
		{
		    InputStream is = entity.getContent();
		    
		    Parser parser = new Parser();
		    
		    try
		    {
			list = parser.getCategories(is);
		    } catch (XmlPullParserException e)
		    {
			e.printStackTrace();
		    }
		    is.close();
		}
	    } catch (ClientProtocolException e)
	    {
		e.printStackTrace();
	    } catch (IOException e)
	    {
		System.out.println("service timeout");
		e.printStackTrace();
	    }
	    
	    // String output = "category content: " + c.getCategoryId() + "-"
	    // + c.getCategoryName();
	    // Log.d("parser returned: ", output);
	    return list;
	}
	
	protected void onPostExecute(List<Category> result)
	{
	    setCategory(result);
	}
    }
    
    public void setCategory(List<Category> result)
    {
	try
	{
	    myApp.categories.clear();
	    myApp.categories.addAll(result);
	    Log.d("ilosc kategori: ", String.valueOf(myApp.categories.size()));
	} catch (NullPointerException e)
	{
	    e.printStackTrace();
	}
	
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
    }
}