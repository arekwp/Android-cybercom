package com.example.restfulclient;

import java.io.IOException;
import java.io.InputStream;

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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.restfulclient.helpers.Category;
import com.example.restfulclient.helpers.MyApplication;
import com.example.restfulclient.helpers.Parser;

public class MainActivity extends Activity implements OnClickListener
{
    MyApplication myApp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	new LongRunningIO().execute("192.168.1.2");
	
	Button button = (Button) findViewById(R.id.bSend);
	
	button.setOnClickListener(this);
	
	myApp = (MyApplication)getApplication();
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
    
    class LongRunningIO extends AsyncTask<String, Void, Category>
    {
	protected Category doInBackground(String... url)
	{
	    String webClientUrl = "http://" + url[0] + ":8020/";
	    String categoryUrl = "categoryservice/category/001";
	    
	    Category c = null;
	    
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
			c = parser.parse(is);
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
	    
	    String output = "category content: " + c.getCategoryId() + "-"
		    + c.getCategoryName();
	    Log.d("parser returned: ", output);
	    return c;
	}
	
	protected void onPostExecute(Category result)
	{
	    setCategory(result);
	}
    }
    
    public void setCategory(Category result)
    {
	Log.d("dodaje kategorie: ", result.getCategoryId() + "/" + result.getCategoryName());
	myApp.categories.add(result);
	Log.d("ilosc kategori: ", String.valueOf(myApp.categories.size()));
    }
}
