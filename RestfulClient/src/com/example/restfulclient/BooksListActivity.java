package com.example.restfulclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import com.example.restfulclient.helpers.Parser;

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
	cat = myApp.c;
	cat.setBooks(new ArrayList<Book>());
	
	new GetBooksThread(myApp.c).execute("192.168.1.2");
	
	Log.d("odebrano id: ", myApp.c.getCategoryId());
	Log.d("wybrano kat: ", cat.getCategoryId());
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    
    class GetBooksThread extends AsyncTask<String, Void, Category>
    {
	Category c;
	public GetBooksThread(Category cin)
        {
	    super();
	    c = cin;
        }

	protected Category doInBackground(String... url)
	{
	    String webClientUrl = "http://" + url[0] + ":8020/";
	    String categoryUrl = "categoryservice/category/" + c.getCategoryId() + "/books";

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
		    
		    c.setBooks(parser.getBooks(is));
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
	    return c;
	}
	
	@Override
	protected void onPostExecute(Category cat)
	{
	    Log.d("added books: ", String.valueOf(cat.getBooks().size()));
	    setCategoryWithBooks(cat);
	}
    }

    public void setCategoryWithBooks(Category cat)
    {
	myApp.c = cat;
	
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
		return (Book) myApp.c.getBooks().toArray()[position];
	    }
	    
	    @Override
	    public int getCount()
	    {
		return myApp.c.getBooks().size();
	    }
	});
    }
}
